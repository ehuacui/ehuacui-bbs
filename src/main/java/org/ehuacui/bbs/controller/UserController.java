package org.ehuacui.bbs.controller;

import org.ehuacui.bbs.common.BaseController;
import org.ehuacui.bbs.common.Constants;
import org.ehuacui.bbs.common.Page;
import org.ehuacui.bbs.interceptor.BeforeAdviceController;
import org.ehuacui.bbs.interceptor.UserInterceptor;
import org.ehuacui.bbs.interceptor.UserStatusInterceptor;
import org.ehuacui.bbs.model.Collect;
import org.ehuacui.bbs.model.Reply;
import org.ehuacui.bbs.model.Topic;
import org.ehuacui.bbs.model.User;
import org.ehuacui.bbs.service.ICollectService;
import org.ehuacui.bbs.service.IReplyService;
import org.ehuacui.bbs.service.ITopicService;
import org.ehuacui.bbs.service.IUserService;
import org.ehuacui.bbs.template.FormatDate;
import org.ehuacui.bbs.template.GetNameByTab;
import org.ehuacui.bbs.template.Marked;
import org.ehuacui.bbs.utils.ResourceUtil;
import org.ehuacui.bbs.utils.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;
    @Autowired
    private ICollectService collectService;
    @Autowired
    private IReplyService replyService;
    @Autowired
    private ITopicService topicService;

    /**
     * 用户个人主页
     */
    @RequestMapping(value = "/{nickname}", method = RequestMethod.GET)
    public String index(HttpServletRequest request, @PathVariable("nickname") String nickname) {
        User currentUser = userService.findByNickname(nickname);
        if (currentUser != null) {
            Long collectCount = collectService.countByUid(currentUser.getId());
            currentUser.setCollectCount(collectCount);
            Page<Topic> topicPage = topicService.pageByAuthor(1, 7, nickname);
            Page<Reply> replyPage = replyService.pageByAuthor(1, 7, nickname);
            request.setAttribute("topicPage", topicPage);
            request.setAttribute("replyPage", replyPage);
            request.setAttribute("currentUser", currentUser);
            request.setAttribute("formatDate", new FormatDate());
            request.setAttribute("getNameByTab", new GetNameByTab());
            request.setAttribute("marked", new Marked());
            request.setAttribute("pageTitle", currentUser.getNickname() + " 个人主页");
        } else {
            request.setAttribute("pageTitle", "用户未找到");
        }
        return "user/info";
    }

    /**
     * 用户的话题列表
     */
    @RequestMapping(value = "/topics/{nickname}", method = RequestMethod.GET)
    public String topics(HttpServletRequest request,
                         @PathVariable("nickname") String nickname,
                         @RequestParam(value = "p", defaultValue = "1") Integer p) {
        User user = userService.findByNickname(nickname);
        request.setAttribute("currentUser", user);
        Page<Topic> page = topicService.pageByAuthor(p, ResourceUtil.getWebConfigIntegerValueByKey("pageSize"), nickname);
        request.setAttribute("page", page);
        request.setAttribute("formatDate", new FormatDate());
        request.setAttribute("getNameByTab", new GetNameByTab());
        return "user/topics";

    }

    /**
     * 用户的回复列表
     */
    @RequestMapping(value = "/replies/{nickname}", method = RequestMethod.GET)
    public String replies(HttpServletRequest request,
                          @PathVariable("nickname") String nickname,
                          @RequestParam(value = "p", defaultValue = "1") Integer p) {
        User user = userService.findByNickname(nickname);
        request.setAttribute("currentUser", user);
        Page<Reply> page = replyService.pageByAuthor(p, ResourceUtil.getWebConfigIntegerValueByKey("pageSize"), nickname);
        request.setAttribute("page", page);
        request.setAttribute("marked", new Marked());
        request.setAttribute("formatDate", new FormatDate());
        return "user/replies";
    }

    /**
     * 用户的话题列表
     */
    @RequestMapping(value = "/collects/{nickname}", method = RequestMethod.GET)
    public String collects(HttpServletRequest request, @PathVariable("nickname") String nickname,
                           @RequestParam(value = "p", defaultValue = "1") Integer p) {
        User user = userService.findByNickname(nickname);
        request.setAttribute("currentUser", user);
        Page<Collect> page = collectService.findByUid(p, ResourceUtil.getWebConfigIntegerValueByKey("pageSize"), user.getId());
        request.setAttribute("page", page);
        request.setAttribute("formatDate", new FormatDate());
        request.setAttribute("getNameByTab", new GetNameByTab());
        return "user/collects";
    }

    /**
     * 用户设置
     */
    @BeforeAdviceController({UserInterceptor.class, UserStatusInterceptor.class})
    @RequestMapping(value = "/setting", method = RequestMethod.POST)
    public String setting(HttpServletRequest request,
                          @RequestParam("url") String url,
                          @RequestParam("signature") String signature,
                          @RequestParam(value = "receive_msg", defaultValue = "0") Integer receive_msg) {
        User user = getUser(request);
        user.setSignature(StringUtil.notBlank(signature) ? Jsoup.clean(signature, Whitelist.basic()) : null);
        user.setUrl(StringUtil.notBlank(url) ? Jsoup.clean(url, Whitelist.basic()) : null);
        user.setReceiveMsg(receive_msg == 1);
        userService.update(user);
        //清理缓存
        try {
            clearCache(Constants.CacheEnum.usernickname.name() + URLEncoder.encode(user.getNickname(), "utf-8"));
        } catch (UnsupportedEncodingException e) {
        }
        clearCache(Constants.CacheEnum.useraccesstoken.name() + user.getAccessToken());
        request.setAttribute("msg", "保存成功。");

        return "user/setting";
    }
}

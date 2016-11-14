package org.ehuacui.bbs.controller;

import org.ehuacui.bbs.dto.PageDataBody;
import org.ehuacui.bbs.interceptor.BeforeAdviceController;
import org.ehuacui.bbs.interceptor.UserInterceptor;
import org.ehuacui.bbs.model.Reply;
import org.ehuacui.bbs.model.Topic;
import org.ehuacui.bbs.model.User;
import org.ehuacui.bbs.service.ReplyService;
import org.ehuacui.bbs.service.TopicService;
import org.ehuacui.bbs.service.UserService;
import org.ehuacui.bbs.template.FormatDate;
import org.ehuacui.bbs.template.GetNameByTab;
import org.ehuacui.bbs.template.Marked;
import org.ehuacui.bbs.utils.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Value("${pageSize}")
    private Integer pageSize;
    @Value("${site.domain}")
    private String siteDomain;

    @Autowired
    private UserService userService;
    @Autowired
    private ReplyService replyService;
    @Autowired
    private TopicService topicService;

    /**
     * 用户个人主页
     */
    @RequestMapping(value = "/{nickname}", method = RequestMethod.GET)
    public String index(HttpServletRequest request, @PathVariable("nickname") String nickname) {
        User currentUser = userService.findByNickname(nickname);
        if (currentUser != null) {
            Long collectCount = topicService.countByUid(currentUser.getId());
            currentUser.setCollectCount(collectCount);
            PageDataBody<Topic> topicPage = topicService.pageByAuthor(1, 7, nickname);
            PageDataBody<Reply> replyPage = replyService.pageByAuthor(1, 7, nickname);
            request.setAttribute("topicPage", topicPage);
            request.setAttribute("replyPage", replyPage);
            request.setAttribute("currentUser", currentUser);
            request.setAttribute("formatDate", new FormatDate());
            request.setAttribute("getNameByTab", new GetNameByTab());
            request.setAttribute("marked", new Marked(siteDomain));
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
        PageDataBody<Topic> page = topicService.pageByAuthor(p, pageSize, nickname);
        request.setAttribute("page", page);
        request.setAttribute("formatDate", new FormatDate());
        request.setAttribute("getNameByTab", new GetNameByTab());
        return "user/topics";

    }

    /**
     * 用户的回复列表
     */
    @RequestMapping(value = "/replies/{nickname}", method = RequestMethod.GET)
    public String replies(HttpServletRequest request, @PathVariable("nickname") String nickname,
                          @RequestParam(value = "p", defaultValue = "1") Integer p) {
        User user = userService.findByNickname(nickname);
        request.setAttribute("currentUser", user);
        PageDataBody<Reply> page = replyService.pageByAuthor(p, pageSize, nickname);
        request.setAttribute("page", page);
        request.setAttribute("marked", new Marked(siteDomain));
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
        PageDataBody<Topic> page = topicService.findByUid(p, pageSize, user.getId());
        request.setAttribute("page", page);
        request.setAttribute("formatDate", new FormatDate());
        request.setAttribute("getNameByTab", new GetNameByTab());
        return "user/collects";
    }

    /**
     * 用户修改密码
     */
    @RequestMapping(value = "/password/update", method = RequestMethod.GET)
    public String updatePassword() {
        return "user/update_password";
    }

    @RequestMapping(value = "/password/update", method = RequestMethod.POST)
    public String updatePassword(HttpServletRequest request, @RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword) {
        User user = getUser(request);
        if (!user.getPassword().equals(oldPassword)) {
            request.setAttribute("errors", "旧密码不正确");
        } else {
            user.setPassword(newPassword);
            userService.update(user);
            setUser(request, user);
            request.setAttribute("msg", "密码修改成功");
        }
        return "user/update_password";
    }

    /**
     * 用户设置
     */
    @RequestMapping(value = "/setting", method = RequestMethod.GET)
    public String setting() {
        return "user/setting";
    }

    @BeforeAdviceController({UserInterceptor.class})
    @RequestMapping(value = "/setting", method = RequestMethod.POST)
    public String setting(HttpServletRequest request, @RequestParam("url") String url,
                          @RequestParam("signature") String signature,
                          @RequestParam(value = "receiveMsg", defaultValue = "0") Integer receiveMsg) {
        User user = getUser(request);
        user.setSignature(StringUtil.notBlank(signature) ? Jsoup.clean(signature, Whitelist.basic()) : null);
        user.setUrl(StringUtil.notBlank(url) ? Jsoup.clean(url, Whitelist.basic()) : null);
        user.setReceiveMsg(receiveMsg == 1);
        userService.update(user);
        setUser(request, user);
        request.setAttribute("msg", "保存成功。");
        return "user/setting";
    }
}

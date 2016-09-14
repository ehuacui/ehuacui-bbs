package org.ehuacui.bbs.controller;

import com.jfinal.kit.PropKit;
import org.ehuacui.bbs.common.BaseController;
import org.ehuacui.bbs.common.Constants;
import org.ehuacui.bbs.common.Page;
import org.ehuacui.bbs.common.ServiceHolder;
import org.ehuacui.bbs.interceptor.BeforeAdviceController;
import org.ehuacui.bbs.interceptor.UserInterceptor;
import org.ehuacui.bbs.interceptor.UserStatusInterceptor;
import org.ehuacui.bbs.model.Collect;
import org.ehuacui.bbs.model.Reply;
import org.ehuacui.bbs.model.Topic;
import org.ehuacui.bbs.model.User;
import org.ehuacui.bbs.template.FormatDate;
import org.ehuacui.bbs.template.GetNameByTab;
import org.ehuacui.bbs.template.Marked;
import org.ehuacui.bbs.utils.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

    /**
     * 用户个人主页
     */
    public void index() throws UnsupportedEncodingException {
        String nickname = getPara(0);
        if (StringUtil.isBlank(nickname)) {
            renderText(Constants.OP_ERROR_MESSAGE);
        } else {
            User currentUser = ServiceHolder.userService.findByNickname(nickname);
            if (currentUser != null) {
                Long collectCount = ServiceHolder.collectService.countByUid(currentUser.getId());
                currentUser.setCollectCount(collectCount);
                Page<Topic> topicPage = ServiceHolder.topicService.pageByAuthor(1, 7, nickname);
                Page<Reply> replyPage = ServiceHolder.replyService.pageByAuthor(1, 7, nickname);
                setAttr("topicPage", topicPage);
                setAttr("replyPage", replyPage);
                setAttr("currentUser", currentUser);
                setAttr("formatDate", new FormatDate());
                setAttr("getNameByTab", new GetNameByTab());
                setAttr("marked", new Marked());
                setAttr("pageTitle", currentUser.getNickname() + " 个人主页");
            } else {
                setAttr("pageTitle", "用户未找到");
            }
            render("user/info.ftl");
        }
    }

    /**
     * 用户的话题列表
     */
    public void topics() throws UnsupportedEncodingException {
        String nickname = getPara(0);
        User user = ServiceHolder.userService.findByNickname(nickname);
        if (user == null) {
            renderText(Constants.OP_ERROR_MESSAGE);
        } else {
            setAttr("currentUser", user);
            Page<Topic> page = ServiceHolder.topicService.pageByAuthor(getParaToInt("p", 1), PropKit.getInt("pageSize"), nickname);
            setAttr("page", page);
            setAttr("formatDate", new FormatDate());
            setAttr("getNameByTab", new GetNameByTab());
            render("user/topics.ftl");
        }
    }

    /**
     * 用户的回复列表
     */
    public void replies() throws UnsupportedEncodingException {
        String nickname = getPara(0);
        User user = ServiceHolder.userService.findByNickname(nickname);
        if (user == null) {
            renderText(Constants.OP_ERROR_MESSAGE);
        } else {
            setAttr("currentUser", user);
            Page<Reply> page = ServiceHolder.replyService.pageByAuthor(getParaToInt("p", 1), PropKit.getInt("pageSize"), nickname);
            setAttr("page", page);
            setAttr("marked", new Marked());
            setAttr("formatDate", new FormatDate());
            render("user/replies.ftl");
        }
    }

    /**
     * 用户的话题列表
     */
    public void collects() throws UnsupportedEncodingException {
        String nickname = getPara(0);
        User user = ServiceHolder.userService.findByNickname(nickname);
        if (user == null) {
            renderText(Constants.OP_ERROR_MESSAGE);
        } else {
            setAttr("currentUser", user);
            Page<Collect> page = ServiceHolder.collectService.findByUid(getParaToInt("p", 1), PropKit.getInt("pageSize"), user.getId());
            setAttr("page", page);
            setAttr("formatDate", new FormatDate());
            setAttr("getNameByTab", new GetNameByTab());
            render("user/collects.ftl");
        }
    }

    /**
     * 用户设置
     */
    @BeforeAdviceController({UserInterceptor.class, UserStatusInterceptor.class})
    public void setting() throws UnsupportedEncodingException {
        String method = getRequest().getMethod();
        if (method.equals("POST")) {
            String url = getPara("url");
            String signature = getPara("signature");
            Integer receive_msg = getParaToInt("receive_msg", 0);
            User user = getUser();
            user.setSignature(StringUtil.notBlank(signature) ? Jsoup.clean(signature, Whitelist.basic()) : null);
            user.setUrl(StringUtil.notBlank(url) ? Jsoup.clean(url, Whitelist.basic()) : null);
            user.setReceiveMsg(receive_msg == 1);
            ServiceHolder.userService.update(user);
            //清理缓存
            clearCache(Constants.CacheEnum.usernickname.name() + URLEncoder.encode(user.getNickname(), "utf-8"));
            clearCache(Constants.CacheEnum.useraccesstoken.name() + user.getAccessToken());
            setAttr("msg", "保存成功。");
        }
        render("user/setting.ftl");
    }

}

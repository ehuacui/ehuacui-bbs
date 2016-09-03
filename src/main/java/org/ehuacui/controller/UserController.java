package org.ehuacui.controller;

import com.jfinal.aop.Before;
import com.jfinal.kit.PropKit;
import org.ehuacui.common.BaseController;
import org.ehuacui.common.Constants;
import org.ehuacui.common.Page;
import org.ehuacui.common.ServiceHolder;
import org.ehuacui.ext.route.ControllerBind;
import org.ehuacui.interceptor.UserInterceptor;
import org.ehuacui.interceptor.UserStatusInterceptor;
import org.ehuacui.model.Collect;
import org.ehuacui.model.Reply;
import org.ehuacui.model.Topic;
import org.ehuacui.model.User;
import org.ehuacui.utils.StrUtil;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
@ControllerBind(controllerKey = "/user", viewPath = "WEB-INF/page")
public class UserController extends BaseController {

    /**
     * 用户个人主页
     */
    public void index() throws UnsupportedEncodingException {
        String nickname = getPara(0);
        if (StrUtil.isBlank(nickname)) {
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
                setAttr("pageTitle", currentUser.getNickname() + " 个人主页");
            } else {
                setAttr("pageTitle", "用户未找到");
            }
            setAttr("currentUser", currentUser);
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
            render("user/collects.ftl");
        }
    }

    /**
     * 用户设置
     */
    @Before({
            UserInterceptor.class,
            UserStatusInterceptor.class
    })
    public void setting() throws UnsupportedEncodingException {
        String method = getRequest().getMethod();
        if (method.equals("POST")) {
            String url = getPara("url");
            String signature = getPara("signature");
            Integer receive_msg = getParaToInt("receive_msg", 0);
            User user = getUser();
            user.setSignature(StrUtil.notBlank(signature) ? Jsoup.clean(signature, Whitelist.basic()) : null);
            user.setUrl(StrUtil.notBlank(url) ? Jsoup.clean(url, Whitelist.basic()) : null);
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

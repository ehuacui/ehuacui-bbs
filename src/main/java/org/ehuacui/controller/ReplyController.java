package org.ehuacui.controller;

import com.jfinal.aop.Before;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.tx.Tx;
import org.ehuacui.common.BaseController;
import org.ehuacui.common.Constants;
import org.ehuacui.common.Constants.CacheEnum;
import org.ehuacui.common.ServiceHolder;
import org.ehuacui.ext.route.ControllerBind;
import org.ehuacui.interceptor.PermissionInterceptor;
import org.ehuacui.interceptor.UserInterceptor;
import org.ehuacui.interceptor.UserStatusInterceptor;
import org.ehuacui.module.Notification;
import org.ehuacui.module.Reply;
import org.ehuacui.module.Topic;
import org.ehuacui.module.User;
import org.ehuacui.utils.StrUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
@ControllerBind(controllerKey = "/reply", viewPath = "WEB-INF/page")
public class ReplyController extends BaseController {

    @Before({
            UserInterceptor.class,
            UserStatusInterceptor.class
    })
    public void save() throws UnsupportedEncodingException {
        String method = getRequest().getMethod();
        if (method.equals("GET")) {
            renderError(404);
        } else if (method.equals("POST")) {
            String content = getPara("content");
            Integer tid = getParaToInt("tid");
            if (tid == null) {
                renderText(Constants.OP_ERROR_MESSAGE);
            } else {
                Date now = new Date();
                User user = getUser();
                Reply reply = new Reply();
                reply.set("tid", tid)
                        .set("content", content)
                        .set("in_time", now)
                        .set("author", user.getStr("nickname"))
                        .set("is_delete", false)
                        .save();
                //topic reply_count++
                Topic topic = ServiceHolder.topicService.findById(tid);
                topic.set("reply_count", topic.getInt("reply_count") + 1)
                        .set("last_reply_time", now)
                        .set("last_reply_author", user.getStr("nickname"))
                        .update();
//                user.set("score", user.getInt("score") + 5).update();
                //发送通知
                //回复者与话题作者不是一个人的时候发送通知
                if (!user.getStr("nickname").equals(topic.getStr("author"))) {
                    ServiceHolder.notificationService.sendNotification(
                            user.getStr("nickname"),
                            topic.getStr("author"),
                            Constants.NotificationEnum.REPLY.name(),
                            tid,
                            content
                    );
                }
                //检查回复内容里有没有at用户,有就发通知
                List<String> atUsers = StrUtil.fetchUsers(content);
                for (String u : atUsers) {
                    if (!u.equals(topic.getStr("author"))) {
                        User _user = ServiceHolder.userService.findByNickname(u);
                        if (_user != null) {
                            ServiceHolder.notificationService.sendNotification(
                                    user.getStr("nickname"),
                                    _user.getStr("nickname"),
                                    Constants.NotificationEnum.AT.name(),
                                    tid,
                                    content
                            );
                        }
                    }
                }
                //清理缓存，保持数据最新
                clearCache(CacheEnum.topic.name() + tid);
                clearCache(CacheEnum.usernickname.name() + URLEncoder.encode(user.getStr("nickname"), "utf-8"));
                clearCache(CacheEnum.useraccesstoken.name() + user.getStr("access_token"));
                redirect("/topic/" + tid + "#reply" + reply.getInt("id"));
            }
        }
    }

    /**
     * 编辑回复
     */
    @Before({
            UserInterceptor.class,
            UserStatusInterceptor.class,
            PermissionInterceptor.class
    })
    public void edit() {
        Integer id = getParaToInt("id");
        String method = getRequest().getMethod();
        Reply reply = ServiceHolder.replyService.findById(id);
        if (method.equals("GET")) {
            Topic topic = ServiceHolder.topicService.findById(reply.getInt("tid"));
            setAttr("reply", reply);
            setAttr("topic", topic);
            render("reply/edit.ftl");
        } else if (method.equals("POST")) {
            String content = getPara("content");
            reply.set("content", content).update();
            redirect("/topic/" + reply.getInt("tid") + "#reply" + id);
        }
    }

    /**
     * 删除回复
     */
    @Before({
            UserInterceptor.class,
            UserStatusInterceptor.class,
            PermissionInterceptor.class,
            Tx.class
    })
    public void delete() throws UnsupportedEncodingException {
        Integer id = getParaToInt("id");
        Reply reply = ServiceHolder.replyService.findById(id);
        Topic topic = ServiceHolder.topicService.findById(reply.getInt("tid"));
        topic.set("reply_count", topic.getInt("reply_count") - 1).update();
        ServiceHolder.replyService.deleteById(id);
        clearCache(CacheEnum.topic.name() + topic.getInt("id"));
        //用户积分计算
//        User user = ServiceHolder.userService.findByNickname(reply.getStr("author"));
//        Integer score = user.getInt("score");
//        score = score > 7 ? score - 7 : 0;
//        user.set("score", score).update();
        //清理缓存
//        clearCache(CacheEnum.usernickname.name() + URLEncoder.encode(user.getStr("nickname"), "utf-8"));
//        clearCache(CacheEnum.useraccesstoken.name() + user.getStr("access_token"));
        redirect("/topic/" + topic.getInt("id"));
    }

    /**
     * 回复列表
     */
    @Before({
            UserInterceptor.class,
            PermissionInterceptor.class
    })
    public void list() {
        setAttr("page", ServiceHolder.replyService.findAll(getParaToInt("p", 1), PropKit.getInt("pageSize")));
        render("reply/list.ftl");
    }
}

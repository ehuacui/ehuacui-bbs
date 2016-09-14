package org.ehuacui.bbs.controller;

import com.jfinal.kit.PropKit;
import org.ehuacui.bbs.common.BaseController;
import org.ehuacui.bbs.common.Constants;
import org.ehuacui.bbs.common.Constants.CacheEnum;
import org.ehuacui.bbs.common.ServiceHolder;
import org.ehuacui.bbs.interceptor.BeforeAdviceController;
import org.ehuacui.bbs.interceptor.PermissionInterceptor;
import org.ehuacui.bbs.interceptor.UserInterceptor;
import org.ehuacui.bbs.interceptor.UserStatusInterceptor;
import org.ehuacui.bbs.model.Reply;
import org.ehuacui.bbs.model.Topic;
import org.ehuacui.bbs.model.User;
import org.ehuacui.bbs.route.ControllerBind;
import org.ehuacui.bbs.template.FormatDate;
import org.ehuacui.bbs.template.Marked;
import org.ehuacui.bbs.utils.StringUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
@ControllerBind(controllerKey = "/reply", viewPath = "WEB-INF/ftl")
public class ReplyController extends BaseController {

    @BeforeAdviceController({
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
                reply.setTid(tid);
                reply.setContent(content);
                reply.setInTime(now);
                reply.setAuthor(user.getNickname());
                reply.setIsDelete(false);
                ServiceHolder.replyService.save(reply);
                //topic reply_count++
                Topic topic = ServiceHolder.topicService.findById(tid);
                topic.setReplyCount(topic.getReplyCount() + 1);
                topic.setLastReplyTime(now);
                topic.setLastReplyAuthor(user.getNickname());
                ServiceHolder.topicService.update(topic);
//                user.set("score", user.getInt("score") + 5).update();
                //发送通知
                //回复者与话题作者不是一个人的时候发送通知
                if (!user.getNickname().equals(topic.getAuthor())) {
                    ServiceHolder.notificationService.sendNotification(
                            user.getNickname(),
                            topic.getAuthor(),
                            Constants.NotificationEnum.REPLY.name(),
                            tid,
                            content
                    );
                }
                //检查回复内容里有没有at用户,有就发通知
                List<String> atUsers = StringUtil.fetchUsers(content);
                for (String u : atUsers) {
                    if (!u.equals(topic.getAuthor())) {
                        User _user = ServiceHolder.userService.findByNickname(u);
                        if (_user != null) {
                            ServiceHolder.notificationService.sendNotification(
                                    user.getNickname(),
                                    _user.getNickname(),
                                    Constants.NotificationEnum.AT.name(),
                                    tid,
                                    content
                            );
                        }
                    }
                }
                //清理缓存，保持数据最新
                clearCache(CacheEnum.topic.name() + tid);
                clearCache(CacheEnum.usernickname.name() + URLEncoder.encode(user.getNickname(), "utf-8"));
                clearCache(CacheEnum.useraccesstoken.name() + user.getAccessToken());
                redirect("/topic/" + tid + "#reply" + reply.getId());
            }
        }
    }

    /**
     * 编辑回复
     */
    @BeforeAdviceController({
            UserInterceptor.class,
            UserStatusInterceptor.class,
            PermissionInterceptor.class
    })
    public void edit() {
        Integer id = getParaToInt("id");
        String method = getRequest().getMethod();
        Reply reply = ServiceHolder.replyService.findById(id);
        if (method.equals("GET")) {
            Topic topic = ServiceHolder.topicService.findById(reply.getTid());
            setAttr("reply", reply);
            setAttr("topic", topic);
            render("reply/edit.ftl");
        } else if (method.equals("POST")) {
            String content = getPara("content");
            reply.setContent(content);
            ServiceHolder.replyService.update(reply);
            redirect("/topic/" + reply.getTid() + "#reply" + id);
        }
    }

    /**
     * 删除回复
     */
    @BeforeAdviceController({
            UserInterceptor.class,
            UserStatusInterceptor.class,
            PermissionInterceptor.class
    })
    public void delete() throws UnsupportedEncodingException {
        Integer id = getParaToInt("id");
        Reply reply = ServiceHolder.replyService.findById(id);
        Topic topic = ServiceHolder.topicService.findById(reply.getTid());
        topic.setReplyCount(topic.getReplyCount() - 1);
        ServiceHolder.topicService.update(topic);
        ServiceHolder.replyService.deleteById(id);
        clearCache(CacheEnum.topic.name() + topic.getId());
        //用户积分计算
//        User user = ServiceHolder.userService.findByNickname(reply.getStr("author"));
//        Integer score = user.getInt("score");
//        score = score > 7 ? score - 7 : 0;
//        user.set("score", score).update();
        //清理缓存
//        clearCache(CacheEnum.usernickname.name() + URLEncoder.encode(user.getStr("nickname"), "utf-8"));
//        clearCache(CacheEnum.useraccesstoken.name() + user.getStr("access_token"));
        redirect("/topic/" + topic.getId());
    }

    /**
     * 回复列表
     */
    @BeforeAdviceController({
            UserInterceptor.class,
            PermissionInterceptor.class
    })
    public void list() {
        setAttr("page", ServiceHolder.replyService.findAll(getParaToInt("p", 1), PropKit.getInt("pageSize")));
        setAttr("formatDate", new FormatDate());
        setAttr("marked", new Marked());
        render("reply/list.ftl");
    }
}

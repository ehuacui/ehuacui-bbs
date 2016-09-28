package org.ehuacui.bbs.controller;

import org.ehuacui.bbs.common.BaseController;
import org.ehuacui.bbs.common.Constants;
import org.ehuacui.bbs.common.Constants.CacheEnum;
import org.ehuacui.bbs.interceptor.BeforeAdviceController;
import org.ehuacui.bbs.interceptor.PermissionInterceptor;
import org.ehuacui.bbs.interceptor.UserInterceptor;
import org.ehuacui.bbs.interceptor.UserStatusInterceptor;
import org.ehuacui.bbs.model.Reply;
import org.ehuacui.bbs.model.Topic;
import org.ehuacui.bbs.model.User;
import org.ehuacui.bbs.service.INotificationService;
import org.ehuacui.bbs.service.IReplyService;
import org.ehuacui.bbs.service.ITopicService;
import org.ehuacui.bbs.service.IUserService;
import org.ehuacui.bbs.template.FormatDate;
import org.ehuacui.bbs.template.Marked;
import org.ehuacui.bbs.utils.ResourceUtil;
import org.ehuacui.bbs.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
@Controller
@RequestMapping("/reply")
public class ReplyController extends BaseController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IReplyService replyService;
    @Autowired
    private ITopicService topicService;
    @Autowired
    private INotificationService notificationService;

    @BeforeAdviceController({UserInterceptor.class, UserStatusInterceptor.class})
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(HttpServletRequest request,
                       @RequestParam("tid") Integer tid,
                       @RequestParam("content") String content) {
        Date now = new Date();
        User user = getUser(request);
        Reply reply = new Reply();
        reply.setTid(tid);
        reply.setContent(content);
        reply.setInTime(now);
        reply.setAuthor(user.getNickname());
        reply.setIsDelete(false);
        replyService.save(reply);
        //topic reply_count++
        Topic topic = topicService.findById(tid);
        topic.setReplyCount(topic.getReplyCount() + 1);
        topic.setLastReplyTime(now);
        topic.setLastReplyAuthor(user.getNickname());
        topicService.update(topic);
//                user.set("score", user.getInt("score") + 5).update();
        //发送通知
        //回复者与话题作者不是一个人的时候发送通知
        if (!user.getNickname().equals(topic.getAuthor())) {
            notificationService.sendNotification(
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
                User _user = userService.findByNickname(u);
                if (_user != null) {
                    notificationService.sendNotification(
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
        try {
            clearCache(CacheEnum.usernickname.name() + URLEncoder.encode(user.getNickname(), "utf-8"));
        } catch (UnsupportedEncodingException e) {
        }
        clearCache(CacheEnum.useraccesstoken.name() + user.getAccessToken());
        return redirect("/topic/" + tid + "#reply" + reply.getId());
    }

    /**
     * 编辑回复
     */
    @BeforeAdviceController({UserInterceptor.class, UserStatusInterceptor.class, PermissionInterceptor.class})
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(HttpServletRequest request, @RequestParam("id") Integer id) {
        Reply reply = replyService.findById(id);
        Topic topic = topicService.findById(reply.getTid());
        request.setAttribute("reply", reply);
        request.setAttribute("topic", topic);
        return "reply/edit";
    }

    @BeforeAdviceController({UserInterceptor.class, UserStatusInterceptor.class, PermissionInterceptor.class})
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(@RequestParam("id") Integer id, @RequestParam("content") String content) {
        Reply reply = replyService.findById(id);
        reply.setContent(content);
        replyService.update(reply);
        return redirect("/topic/" + reply.getTid() + "#reply" + id);
    }

    /**
     * 删除回复
     */
    @BeforeAdviceController({UserInterceptor.class, UserStatusInterceptor.class, PermissionInterceptor.class})
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam("id") Integer id) {
        Reply reply = replyService.findById(id);
        Topic topic = topicService.findById(reply.getTid());
        topic.setReplyCount(topic.getReplyCount() - 1);
        topicService.update(topic);
        replyService.deleteById(id);
        clearCache(CacheEnum.topic.name() + topic.getId());
        //用户积分计算
//        User user = userService.findByNickname(reply.getStr("author"));
//        Integer score = user.getInt("score");
//        score = score > 7 ? score - 7 : 0;
//        user.set("score", score).update();
        //清理缓存
//        clearCache(CacheEnum.usernickname.name() + URLEncoder.encode(user.getStr("nickname"), "utf-8"));
//        clearCache(CacheEnum.useraccesstoken.name() + user.getStr("access_token"));
        return redirect("/topic/" + topic.getId());
    }

    /**
     * 回复列表
     */
    @BeforeAdviceController({UserInterceptor.class, PermissionInterceptor.class})
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(@RequestParam(value = "p", defaultValue = "1") Integer p, HttpServletRequest request) {
        request.setAttribute("page", replyService.findAll(p, ResourceUtil.getWebConfigIntegerValueByKey("pageSize")));
        request.setAttribute("formatDate", new FormatDate());
        request.setAttribute("marked", new Marked());
        return ("reply/list");
    }
}

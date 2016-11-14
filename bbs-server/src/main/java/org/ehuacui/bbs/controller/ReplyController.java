package org.ehuacui.bbs.controller;

import org.ehuacui.bbs.dto.Constants;
import org.ehuacui.bbs.interceptor.BeforeAdviceController;
import org.ehuacui.bbs.interceptor.PermissionInterceptor;
import org.ehuacui.bbs.interceptor.UserInterceptor;
import org.ehuacui.bbs.model.Reply;
import org.ehuacui.bbs.model.Topic;
import org.ehuacui.bbs.model.User;
import org.ehuacui.bbs.service.NotificationService;
import org.ehuacui.bbs.service.ReplyService;
import org.ehuacui.bbs.service.TopicService;
import org.ehuacui.bbs.service.UserService;
import org.ehuacui.bbs.template.FormatDate;
import org.ehuacui.bbs.template.Marked;
import org.ehuacui.bbs.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    private NotificationService notificationService;

    @BeforeAdviceController({UserInterceptor.class})
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(HttpServletRequest request, @RequestParam("tid") Integer tid, @RequestParam("content") String content) {
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
            notificationService.sendNotification(user.getNickname(), topic.getAuthor(),
                    Constants.NotificationEnum.REPLY.name(), tid, content);
        }
        //检查回复内容里有没有at用户,有就发通知
        List<String> atUsers = StringUtil.fetchUsers(content);
        for (String u : atUsers) {
            if (!u.equals(topic.getAuthor())) {
                User _user = userService.findByNickname(u);
                if (_user != null) {
                    notificationService.sendNotification(user.getNickname(), _user.getNickname(),
                            Constants.NotificationEnum.AT.name(), tid, content);
                }
            }
        }
        return redirect("/topic/" + tid + "#reply" + reply.getId());
    }

    /**
     * 编辑回复
     */
    @BeforeAdviceController({UserInterceptor.class, PermissionInterceptor.class})
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(HttpServletRequest request, @RequestParam("id") Integer id) {
        Reply reply = replyService.findById(id);
        Topic topic = topicService.findById(reply.getTid());
        request.setAttribute("reply", reply);
        request.setAttribute("topic", topic);
        return "reply/edit";
    }

    @BeforeAdviceController({UserInterceptor.class, PermissionInterceptor.class})
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
    @BeforeAdviceController({UserInterceptor.class, PermissionInterceptor.class})
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam("id") Integer id) {
        Reply reply = replyService.findById(id);
        Topic topic = topicService.findById(reply.getTid());
        topic.setReplyCount(topic.getReplyCount() - 1);
        topicService.update(topic);
        replyService.deleteById(id);
        //用户积分计算
//        User user = userService.findByNickname(reply.getStr("author"));
//        Integer score = user.getInt("score");
//        score = score > 7 ? score - 7 : 0;
//        user.set("score", score).update();
        return redirect("/topic/" + topic.getId());
    }

    /**
     * 回复列表
     */
    @BeforeAdviceController({UserInterceptor.class, PermissionInterceptor.class})
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(@RequestParam(value = "p", defaultValue = "1") Integer p, HttpServletRequest request) {
        request.setAttribute("page", replyService.findAll(p, pageSize));
        request.setAttribute("formatDate", new FormatDate());
        request.setAttribute("marked", new Marked(siteDomain));
        return "reply/list";
    }
}

package org.ehuacui.bbs.controller;

import org.ehuacui.bbs.dto.Constants;
import org.ehuacui.bbs.interceptor.BeforeAdviceController;
import org.ehuacui.bbs.interceptor.UserInterceptor;
import org.ehuacui.bbs.model.Collect;
import org.ehuacui.bbs.model.Topic;
import org.ehuacui.bbs.model.User;
import org.ehuacui.bbs.service.CollectService;
import org.ehuacui.bbs.service.NotificationService;
import org.ehuacui.bbs.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
@Controller
@RequestMapping("/collect")
public class CollectController extends BaseController {
    @Autowired
    private CollectService collectService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private NotificationService notificationService;

    /**
     * 收藏话题
     */
    @BeforeAdviceController(UserInterceptor.class)
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(@RequestParam("tid") Integer tid, HttpServletRequest request) {
        Date now = new Date();
        User user = getUser(request);
        Collect collect = new Collect();
        collect.setTid(tid);
        collect.setUid(user.getId());
        collect.setInTime(now);
        collectService.save(collect);
        Topic topic = topicService.findById(tid);
        //回复者与话题作者不是一个人的时候发送通知
        if (!user.getNickname().equals(topic.getAuthor())) {
            //创建通知
            notificationService.sendNotification(user.getNickname(), topic.getAuthor(), Constants.NotificationEnum.COLLECT.name(), tid, "");
        }
        return redirect("/topic/" + tid);
    }

    /**
     * 取消收藏话题
     */
    @BeforeAdviceController(UserInterceptor.class)
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam("tid") Integer tid, HttpServletRequest request) {
        User user = getUser(request);
        Collect collect = collectService.findByTidAndUid(tid, user.getId());
        if (collect != null) {
            collectService.delete(collect.getId());
        }
        return redirect("/topic/" + tid);
    }
}

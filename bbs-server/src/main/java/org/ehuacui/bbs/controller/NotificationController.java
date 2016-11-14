package org.ehuacui.bbs.controller;

import org.ehuacui.bbs.dto.PageDataBody;
import org.ehuacui.bbs.interceptor.BeforeAdviceController;
import org.ehuacui.bbs.interceptor.UserInterceptor;
import org.ehuacui.bbs.model.Notification;
import org.ehuacui.bbs.model.User;
import org.ehuacui.bbs.service.NotificationService;
import org.ehuacui.bbs.template.FormatDate;
import org.ehuacui.bbs.template.GetAvatarByNickname;
import org.ehuacui.bbs.template.Marked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/notification")
public class NotificationController extends BaseController {

    @Value("${pageSize}")
    private Integer pageSize;
    @Value("${site.domain}")
    private String siteDomain;

    @Autowired
    private NotificationService notificationService;

    @BeforeAdviceController(UserInterceptor.class)
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(@RequestParam(value = "p", defaultValue = "1") Integer p, HttpServletRequest request) {
        User user = getUser(request);
        PageDataBody<Notification> page = notificationService.pageByAuthor(p, pageSize, user.getNickname());
        //将通知都设置成已读的
        notificationService.makeUnreadToRead(user.getNickname());
        request.setAttribute("page", page);
        request.setAttribute("formatDate", new FormatDate());
        request.setAttribute("getAvatarByNickname", new GetAvatarByNickname());
        request.setAttribute("marked", new Marked(siteDomain));
        return "notification/index";
    }
}

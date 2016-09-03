package org.ehuacui.controller;

import com.jfinal.aop.Before;
import com.jfinal.kit.PropKit;
import org.ehuacui.common.BaseController;
import org.ehuacui.common.Page;
import org.ehuacui.common.ServiceHolder;
import org.ehuacui.ext.route.ControllerBind;
import org.ehuacui.interceptor.UserInterceptor;
import org.ehuacui.model.Notification;
import org.ehuacui.model.User;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
@ControllerBind(controllerKey = "/notification", viewPath = "WEB-INF/page")
public class NotificationController extends BaseController {

    @Before(UserInterceptor.class)
    public void index() {
        User user = getUser();
        Page<Notification> page = ServiceHolder.notificationService.pageByAuthor(getParaToInt("p", 1), PropKit.getInt("pageSize"), user.getNickname());
        //将通知都设置成已读的
        ServiceHolder.notificationService.makeUnreadToRead(user.getNickname());
        setAttr("page", page);
        render("notification/index.ftl");
    }
}

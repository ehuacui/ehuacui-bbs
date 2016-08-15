package org.ehuacui.controller;

import com.jfinal.aop.Before;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Page;
import org.ehuacui.common.BaseController;
import org.ehuacui.common.ServiceHolder;
import org.ehuacui.ext.route.ControllerBind;
import org.ehuacui.interceptor.UserInterceptor;
import org.ehuacui.module.Notification;
import org.ehuacui.module.User;

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
        Page<Notification> page = ServiceHolder.notificationService.pageByAuthor(getParaToInt("p", 1), PropKit.getInt("pageSize"), user.getStr("nickname"));
        //将通知都设置成已读的
        ServiceHolder.notificationService.makeUnreadToRead(user.getStr("nickname"));
        setAttr("page", page);
        render("notification/index.ftl");
    }
}

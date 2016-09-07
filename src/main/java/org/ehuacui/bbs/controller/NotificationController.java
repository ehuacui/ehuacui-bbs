package org.ehuacui.bbs.controller;

import com.jfinal.aop.Before;
import com.jfinal.kit.PropKit;
import org.ehuacui.bbs.common.BaseController;
import org.ehuacui.bbs.common.Page;
import org.ehuacui.bbs.common.ServiceHolder;
import org.ehuacui.bbs.ext.route.ControllerBind;
import org.ehuacui.bbs.interceptor.UserInterceptor;
import org.ehuacui.bbs.model.Notification;
import org.ehuacui.bbs.model.User;
import org.ehuacui.bbs.template.FormatDate;
import org.ehuacui.bbs.template.GetAvatarByNickname;
import org.ehuacui.bbs.template.Marked;

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
        setAttr("formatDate", new FormatDate());
        setAttr("getAvatarByNickname", new GetAvatarByNickname());
        setAttr("marked", new Marked());
        render("notification/index.ftl");
    }
}

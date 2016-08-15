package org.ehuacui.controller;

import com.jfinal.aop.Before;
import org.ehuacui.common.BaseController;
import org.ehuacui.common.Constants;
import org.ehuacui.common.Constants.CacheEnum;
import org.ehuacui.common.ServiceHolder;
import org.ehuacui.ext.route.ControllerBind;
import org.ehuacui.interceptor.UserInterceptor;
import org.ehuacui.module.Collect;
import org.ehuacui.module.Notification;
import org.ehuacui.module.Topic;
import org.ehuacui.module.User;

import java.util.Date;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
@ControllerBind(controllerKey = "/collect", viewPath = "WEB-INF/page")
public class CollectController extends BaseController {

    /**
     * 收藏话题
     */
    @Before(UserInterceptor.class)
    public void add() {
        Integer tid = getParaToInt("tid");
        Date now = new Date();
        User user = getUser();
        Collect collect = new Collect();
        collect.set("tid", tid)
                .set("uid", user.getInt("id"))
                .set("in_time", now)
                .save();
        Topic topic = ServiceHolder.topicService.findById(tid);
        //创建通知
        ServiceHolder.notificationService.sendNotification(
                user.getStr("nickname"),
                topic.getStr("author"),
                Constants.NotificationEnum.COLLECT.name(),
                tid,
                ""
        );
        //清理缓存
        clearCache(CacheEnum.usercollectcount.name() + user.getInt("id"));
        clearCache(CacheEnum.collects.name() + user.getInt("id"));
        clearCache(CacheEnum.collectcount.name() + tid);
        clearCache(CacheEnum.collect.name() + tid + "_" + user.getInt("id"));
        redirect("/topic/" + tid);
    }

    /**
     * 取消收藏话题
     */
    @Before(UserInterceptor.class)
    public void delete() {
        Integer tid = getParaToInt("tid");
        User user = getUser();
        Collect collect = ServiceHolder.collectService.findByTidAndUid(tid, user.getInt("id"));
        if (collect == null) {
            renderText("请先收藏");
        } else {
            collect.delete();
            clearCache(CacheEnum.usercollectcount.name() + user.getInt("id"));
            clearCache(CacheEnum.collects.name() + user.getInt("id"));
            clearCache(CacheEnum.collectcount.name() + tid);
            clearCache(CacheEnum.collect.name() + tid + "_" + user.getInt("id"));
            redirect("/topic/" + tid);
        }
    }
}

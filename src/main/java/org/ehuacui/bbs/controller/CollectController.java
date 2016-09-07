package org.ehuacui.bbs.controller;

import com.jfinal.aop.Before;
import org.ehuacui.bbs.common.BaseController;
import org.ehuacui.bbs.common.Constants;
import org.ehuacui.bbs.common.Constants.CacheEnum;
import org.ehuacui.bbs.common.ServiceHolder;
import org.ehuacui.bbs.route.ControllerBind;
import org.ehuacui.bbs.interceptor.UserInterceptor;
import org.ehuacui.bbs.model.Collect;
import org.ehuacui.bbs.model.Topic;
import org.ehuacui.bbs.model.User;

import java.util.Date;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
@ControllerBind(controllerKey = "/collect", viewPath = "WEB-INF/ftl")
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
        collect.setTid(tid);
        collect.setUid(user.getId());
        collect.setInTime(now);
        ServiceHolder.collectService.save(collect);
        Topic topic = ServiceHolder.topicService.findById(tid);
        //创建通知
        ServiceHolder.notificationService.sendNotification(user.getNickname(),
                topic.getAuthor(), Constants.NotificationEnum.COLLECT.name(), tid, "");
        //清理缓存
        clearCache(CacheEnum.usercollectcount.name() + user.getId());
        clearCache(CacheEnum.collects.name() + user.getId());
        clearCache(CacheEnum.collectcount.name() + tid);
        clearCache(CacheEnum.collect.name() + tid + "_" + user.getId());
        redirect("/topic/" + tid);
    }

    /**
     * 取消收藏话题
     */
    @Before(UserInterceptor.class)
    public void delete() {
        Integer tid = getParaToInt("tid");
        User user = getUser();
        Collect collect = ServiceHolder.collectService.findByTidAndUid(tid, user.getId());
        if (collect == null) {
            renderText("请先收藏");
        } else {
            ServiceHolder.collectService.delete(collect.getId());
            clearCache(CacheEnum.usercollectcount.name() + user.getId());
            clearCache(CacheEnum.collects.name() + user.getId());
            clearCache(CacheEnum.collectcount.name() + tid);
            clearCache(CacheEnum.collect.name() + tid + "_" + user.getId());
            redirect("/topic/" + tid);
        }
    }
}

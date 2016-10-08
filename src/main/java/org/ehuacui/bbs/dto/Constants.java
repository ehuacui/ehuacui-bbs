package org.ehuacui.bbs.dto;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
public class Constants {

    // COOKIE SESSION变量KEY
    public static final String USER_ACCESS_TOKEN = "user_access_token";
    // 接口返回描述
    public static final String OP_ERROR_MESSAGE = "非法操作";

    //缓存的key
    public enum CacheEnum {
        section,
        sections,
        topic,
        topicappends,
        usernickname,
        useraccesstoken,
        userpermissions,
        collect,
        collects,
        collectcount,
        usercollectcount
    }

    //第三方登录渠道
    public enum LoginEnum {
        Github
    }

    //通知事件
    public enum NotificationEnum {
        REPLY,
        COLLECT,
        AT
    }

}
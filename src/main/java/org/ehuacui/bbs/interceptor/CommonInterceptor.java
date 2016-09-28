package org.ehuacui.bbs.interceptor;

import org.ehuacui.bbs.common.Constants;
import org.ehuacui.bbs.model.User;
import org.ehuacui.bbs.service.INotificationService;
import org.ehuacui.bbs.service.IUserService;
import org.ehuacui.bbs.utils.ResourceUtil;
import org.ehuacui.bbs.utils.StringUtil;
import org.ehuacui.bbs.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
public class CommonInterceptor implements Interceptor {

    @Autowired
    private IUserService userService;
    @Autowired
    private INotificationService notificationService;

    public void invoke(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String> propMap = ResourceUtil.readWebConfigProperties();
        String user_cookie = WebUtil.getCookie(request, Constants.USER_ACCESS_TOKEN);
        if (StringUtil.notBlank(user_cookie)) {
            String user_access_token = StringUtil.getDecryptToken(user_cookie);
            User user = userService.findByAccessToken(user_access_token);
            if (user == null) {
                WebUtil.removeCookie(response, Constants.USER_ACCESS_TOKEN, "/", propMap.get("cookie.domain"));
            } else {
                int count = notificationService.findNotReadCount(user.getNickname());
                request.setAttribute("notifications", count == 0 ? null : count);
                request.setAttribute("userinfo", user);
            }
        }

        //如果是微博登录的话,要在页面头部添加meta标签
        String loginChannel = propMap.get("login.channel");
        Map<String, String> loginChannelMap = new HashMap<String, String>();
        if (StringUtil.isBlank(loginChannel) || loginChannel.equals(Constants.LoginEnum.Github.name())) {
            loginChannelMap.put("loginChannelName", Constants.LoginEnum.Github.name());
            loginChannelMap.put("loginChannelUrl", "/oauth/githublogin");
            request.setAttribute("login_channel", loginChannelMap);
        }
        String solrStatus = propMap.get("solr.status").equalsIgnoreCase("true") ? "true" : "false";
        request.setAttribute("solrStatus", solrStatus);
        request.setAttribute("shareDomain", propMap.get("share.domain"));
        request.setAttribute("siteTitle", propMap.get("siteTitle"));
        request.setAttribute("beianName", propMap.get("beianName"));
        request.setAttribute("tongjiJs", propMap.get("tongjiJs"));
    }
}

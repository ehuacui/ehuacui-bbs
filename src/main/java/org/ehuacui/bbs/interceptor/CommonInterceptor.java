package org.ehuacui.bbs.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import org.ehuacui.bbs.common.Constants;
import org.ehuacui.bbs.common.ServiceHolder;
import org.ehuacui.bbs.model.User;
import org.ehuacui.bbs.utils.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
public class CommonInterceptor implements Interceptor {

    public void intercept(Invocation inv) {
        Controller controller = inv.getController();
        PropKit.use("config.properties");

        String user_cookie = controller.getCookie(Constants.USER_ACCESS_TOKEN);

        if (StringUtil.notBlank(user_cookie)) {
            String user_access_token = StringUtil.getDecryptToken(user_cookie);
            User user = ServiceHolder.userService.findByAccessToken(user_access_token);
            if (user == null) {
                controller.removeCookie(Constants.USER_ACCESS_TOKEN, "/", PropKit.get("cookie.domain"));
            } else {
                int count = ServiceHolder.notificationService.findNotReadCount(user.getNickname());
                controller.setAttr("notifications", count == 0 ? null : count);
                controller.setAttr("userinfo", user);
            }
        }

        //如果是微博登录的话,要在页面头部添加meta标签
        String loginChannel = PropKit.get("login.channel");
        Map<String, String> loginChannelMap = new HashMap<String, String>();
        if (StringUtil.isBlank(loginChannel) || loginChannel.equals(Constants.LoginEnum.Github.name())) {
            loginChannelMap.put("loginChannelName", Constants.LoginEnum.Github.name());
            loginChannelMap.put("loginChannelUrl", "/oauth/githublogin");
            controller.setAttr("login_channel", loginChannelMap);
        }
        controller.setAttr("solrStatus", PropKit.getBoolean("solr.status") ? "true" : "false");
        controller.setAttr("shareDomain", PropKit.get("share.domain"));
        controller.setAttr("siteTitle", PropKit.get("siteTitle"));
        controller.setAttr("beianName", PropKit.get("beianName"));
        controller.setAttr("tongjiJs", PropKit.get("tongjiJs"));
        inv.invoke();
    }
}

package org.ehuacui.bbs.interceptor;

import org.ehuacui.bbs.common.Constants;
import org.ehuacui.bbs.model.User;
import org.ehuacui.bbs.service.IUserService;
import org.ehuacui.bbs.utils.StringUtil;
import org.ehuacui.bbs.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
public class UserInterceptor implements Interceptor {

    @Autowired
    private IUserService userService;

    @Override
    public void invoke(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String user_cookie = WebUtil.getCookie(request, Constants.USER_ACCESS_TOKEN);
        boolean flag = false;
        User user;
        if (StringUtil.notBlank(user_cookie)) {
            user = userService.findByAccessToken(StringUtil.getDecryptToken(user_cookie));
            if (user != null) {
                flag = true;
            }
        }
        if (!flag) {
            String queryString = request.getQueryString();
            String beforeUrl = request.getRequestURL() + "?" + queryString;
            if (StringUtil.isBlank(queryString)) {
                beforeUrl = request.getRequestURL().toString();
            }
            response.sendRedirect("/login?callback=" + URLEncoder.encode(beforeUrl, "UTF-8"));
        }
    }

}

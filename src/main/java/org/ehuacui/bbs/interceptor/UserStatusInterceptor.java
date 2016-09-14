package org.ehuacui.bbs.interceptor;

import org.ehuacui.bbs.common.Constants;
import org.ehuacui.bbs.common.ServiceHolder;
import org.ehuacui.bbs.model.User;
import org.ehuacui.bbs.utils.StringUtil;
import org.ehuacui.bbs.utils.WebUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
public class UserStatusInterceptor implements Interceptor {

    @Override
    public void invoke(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String user_cookie = WebUtil.getCookie(request, Constants.USER_ACCESS_TOKEN);
        User user = ServiceHolder.userService.findByAccessToken(StringUtil.getDecryptToken(user_cookie));
        if (user.getIsBlock()) {
            response.sendRedirect("/403");
        }
    }

}

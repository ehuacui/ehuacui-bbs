package org.ehuacui.bbs.interceptor;

import org.ehuacui.bbs.common.Constants;
import org.ehuacui.bbs.common.ServiceHolder;
import org.ehuacui.bbs.model.User;
import org.ehuacui.bbs.utils.StringUtil;
import org.ehuacui.bbs.utils.WebUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
public class PermissionInterceptor implements Interceptor {

    @Override
    public void invoke(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String user_cookie = WebUtil.getCookie(request, Constants.USER_ACCESS_TOKEN);
        User user = ServiceHolder.userService.findByAccessToken(StringUtil.getDecryptToken(user_cookie));
        //处理权限部分
        Map<String, String> permissions = ServiceHolder.permissionService.findPermissions(user.getId());
        String path = request.getServletPath();
        //没有权限
        if (!permissions.containsValue(path)) {
            response.sendRedirect("/401");
        }
    }
}

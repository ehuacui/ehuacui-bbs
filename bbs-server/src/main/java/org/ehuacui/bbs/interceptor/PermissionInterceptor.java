package org.ehuacui.bbs.interceptor;

import org.ehuacui.bbs.dto.Constants;
import org.ehuacui.bbs.model.User;
import org.ehuacui.bbs.service.PermissionService;
import org.ehuacui.bbs.service.UserService;
import org.ehuacui.bbs.utils.StringUtil;
import org.ehuacui.bbs.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
public class PermissionInterceptor implements Interceptor {

    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;

    @Override
    public void invoke(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = null;
        Object sessionObject = request.getSession().getAttribute("user");
        if (sessionObject != null) {
            user = (User) sessionObject;
        }
        if (user == null) {
            String user_cookie = WebUtil.getCookie(request, Constants.USER_ACCESS_TOKEN);
            if (StringUtil.notBlank(user_cookie)) {
                user = userService.findByAccessToken(StringUtil.getDecryptToken(user_cookie));
            }
        }
        if (user != null) {
            //处理权限部分
            Map<String, String> permissions = permissionService.findPermissions(user.getId());
            String path = request.getServletPath();
            //没有权限
            if (!permissions.containsValue(path)) {
                //response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.sendRedirect("/401.html");
            }
        }
    }
}

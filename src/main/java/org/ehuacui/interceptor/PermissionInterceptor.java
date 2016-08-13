package org.ehuacui.interceptor;

import org.ehuacui.common.Constants;
import org.ehuacui.module.Permission;
import org.ehuacui.module.User;
import org.ehuacui.utils.StrUtil;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
public class PermissionInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation inv) {
        Controller controller = inv.getController();
        HttpServletRequest request = controller.getRequest();
        String user_cookie = controller.getCookie(Constants.USER_ACCESS_TOKEN);

        User user = User.me.findByAccessToken(StrUtil.getDecryptToken(user_cookie));

        //处理权限部分
        Map<String, String> permissions = Permission.me.findPermissions(user.getInt("id"));
        //String path = request.getRequestURI();
        String path = request.getServletPath();
        if (permissions.containsValue(path)) {
            inv.invoke();
        } else {
            //没有权限
            controller.renderError(401);
        }
//        inv.invoke();
    }
}

package org.ehuacui.interceptor;

import org.ehuacui.common.Constants;
import org.ehuacui.common.ServiceHolder;
import org.ehuacui.module.User;
import org.ehuacui.utils.StrUtil;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
public class UserInterceptor implements Interceptor {

    @Override
    public void intercept(Invocation inv) {
        Controller controller = inv.getController();
        HttpServletRequest request = controller.getRequest();
        String user_cookie = controller.getCookie(Constants.USER_ACCESS_TOKEN);

        boolean flag = false;
        User user = null;
        if(StrUtil.notBlank(user_cookie)) {
            user = ServiceHolder.userService.findByAccessToken(StrUtil.getDecryptToken(user_cookie));
            if(user != null) {
                flag = true;
            }
        }

        if(flag) {
            inv.invoke();
        } else {
            String querystring = request.getQueryString();
            String beforeUrl = request.getRequestURL() + "?" + querystring;
            if(StrUtil.isBlank(querystring)) {
                beforeUrl = request.getRequestURL().toString();
            }
            try {
                controller.redirect("/login?callback=" + URLEncoder.encode(beforeUrl, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

}

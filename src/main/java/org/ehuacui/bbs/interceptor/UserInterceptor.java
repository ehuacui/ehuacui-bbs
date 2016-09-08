package org.ehuacui.bbs.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import org.ehuacui.bbs.common.ServiceHolder;
import org.ehuacui.bbs.model.User;
import org.ehuacui.bbs.utils.StringUtil;
import org.ehuacui.bbs.common.Constants;

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
        if (StringUtil.notBlank(user_cookie)) {
            user = ServiceHolder.userService.findByAccessToken(StringUtil.getDecryptToken(user_cookie));
            if (user != null) {
                flag = true;
            }
        }

        if (flag) {
            inv.invoke();
        } else {
            String querystring = request.getQueryString();
            String beforeUrl = request.getRequestURL() + "?" + querystring;
            if (StringUtil.isBlank(querystring)) {
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

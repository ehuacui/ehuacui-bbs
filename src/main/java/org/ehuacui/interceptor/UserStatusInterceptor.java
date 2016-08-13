package org.ehuacui.interceptor;

import org.ehuacui.common.Constants;
import org.ehuacui.module.User;
import org.ehuacui.utils.StrUtil;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
public class UserStatusInterceptor implements Interceptor {

    @Override
    public void intercept(Invocation inv) {
        Controller controller = inv.getController();
        String user_cookie = controller.getCookie(Constants.USER_ACCESS_TOKEN);

        User user = User.me.findByAccessToken(StrUtil.getDecryptToken(user_cookie));
        if(user.getBoolean("isblock")) {
            controller.renderText("您的账户已被禁用!");
        } else {
            inv.invoke();
        }

    }

}

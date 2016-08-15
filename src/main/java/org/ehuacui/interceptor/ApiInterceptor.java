package org.ehuacui.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import org.ehuacui.common.ServiceHolder;
import org.ehuacui.module.User;
import org.ehuacui.utils.Result;
import org.ehuacui.utils.StrUtil;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
public class ApiInterceptor implements Interceptor {

    @Override
    public void intercept(Invocation inv) {
        Controller controller = inv.getController();
        String token = controller.getPara("token");

        boolean flag = false;
        User user;
        String msg = "请先登录";
        if (StrUtil.notBlank(token)) {
            user = ServiceHolder.userService.findByAccessToken(token);
            if (user != null) {
                if (user.getBoolean("is_block")) {
                    msg = "您的账户已被禁用";
                } else {
                    flag = true;
                }
            }
        }

        if (flag) {
            inv.invoke();
        } else {
            controller.renderJson(new Result("201", msg, null));
        }
    }

}

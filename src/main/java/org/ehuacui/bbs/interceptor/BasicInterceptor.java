package org.ehuacui.bbs.interceptor;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 基础拦截器
 * Created by jianwei.zhou on 2016/9/13.
 */
public class BasicInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            BeforeAdviceController authPassport = ((HandlerMethod) handler).getMethodAnnotation(BeforeAdviceController.class);
            authPassport.value();
        }
        return false;
    }
}

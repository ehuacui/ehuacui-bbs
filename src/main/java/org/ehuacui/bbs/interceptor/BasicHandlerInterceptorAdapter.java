package org.ehuacui.bbs.interceptor;

import org.ehuacui.bbs.common.WebApplicationContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 基础拦截器
 * Created by jianwei.zhou on 2016/9/13.
 */
public class BasicHandlerInterceptorAdapter extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Class<?> classController = handlerMethod.getBeanType();
            BeforeAdviceController beforeAdviceControllerClass = classController.getAnnotation(BeforeAdviceController.class);
            BeforeAdviceController beforeAdviceControllerMethod = handlerMethod.getMethodAnnotation(BeforeAdviceController.class);
            if (beforeAdviceControllerMethod != null) {
                Class<? extends Interceptor>[] methodInterceptors = beforeAdviceControllerMethod.value();
                if (methodInterceptors != null) {
                    for (int i = 0; i < methodInterceptors.length; i++) {
                        Class<? extends Interceptor> interceptor = methodInterceptors[i];
                        Object object = WebApplicationContextHolder.getBean(interceptor);
                        Method method = interceptor.getDeclaredMethod("invoke", HttpServletRequest.class, HttpServletResponse.class);
                        method.invoke(object, request, response);
                    }
                }
            }
        }
        return true;
    }

}

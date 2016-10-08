package org.ehuacui.bbs.interceptor;

import org.ehuacui.bbs.config.WebApplicationContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 基础拦截器
 * Created by jianwei.zhou on 2016/9/13.
 */
public class BasicHandlerInterceptorAdapter extends HandlerInterceptorAdapter {

    private Set<? extends Interceptor> defaultInterceptors;//默认拦截器集合

    public void setDefaultInterceptors(Set<? extends Interceptor> defaultInterceptors) {
        this.defaultInterceptors = defaultInterceptors;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Class<?> classController = handlerMethod.getBeanType();
            Method controllerMethod = handlerMethod.getMethod();
            Set<Class<? extends Interceptor>> interceptors = new LinkedHashSet<>();//拦截器集合
            if (defaultInterceptors != null && defaultInterceptors.size() > 0) {
                Iterator<? extends Interceptor> iterator = defaultInterceptors.iterator();
                while (iterator.hasNext()) {
                    interceptors.add(iterator.next().getClass());
                }
            }
            //配置在类上的拦截器注解
            BeforeAdviceController beforeAdviceControllerClass = classController.getAnnotation(BeforeAdviceController.class);
            if (beforeAdviceControllerClass != null) {
                Class<? extends Interceptor>[] classInterceptors = beforeAdviceControllerClass.value();
                if (classInterceptors != null && classInterceptors.length > 0) {
                    interceptors.addAll(Arrays.asList(classInterceptors));
                }
            }
            //配置在方法上的拦截器注解
            BeforeAdviceController beforeAdviceControllerMethod = controllerMethod.getAnnotation(BeforeAdviceController.class);
            if (beforeAdviceControllerMethod != null) {
                Class<? extends Interceptor>[] methodInterceptors = beforeAdviceControllerMethod.value();
                if (methodInterceptors != null && methodInterceptors.length > 0) {
                    interceptors.addAll(Arrays.asList(methodInterceptors));
                }
            }
            //调用拦截器方法
            for (Class<? extends Interceptor> interceptor : interceptors) {
                Object object = WebApplicationContextHolder.getBean(interceptor);
                Method method = interceptor.getDeclaredMethod("invoke", HttpServletRequest.class, HttpServletResponse.class);
                method.invoke(object, request, response);
            }
        }
        return true;
    }

}

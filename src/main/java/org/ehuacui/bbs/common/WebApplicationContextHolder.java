package org.ehuacui.bbs.common;

import org.springframework.context.ApplicationContext;

/**
 * WebApplicationContextHolder
 * Created by Administrator on 2016/9/14.
 */
public class WebApplicationContextHolder {
    private static ApplicationContext applicationContext;

    /**
     * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
     */
    public static void setApplicationContext(ApplicationContext applicationContext) {
        WebApplicationContextHolder.applicationContext = applicationContext;
    }

    /**
     * 从静态变量ApplicationContext中取得Bean,自动转型为所赋值对象的类型.
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        checkApplicationContext();
        return applicationContext.getBean(name, clazz);
    }

    /**
     * 从静态变量ApplicationContext中取得Bean,自动转型为所赋值对象的类型.
     */
    public static <T> T getBean(Class<T> clazz) {
        checkApplicationContext();
        return applicationContext.getBean(clazz);
    }

    private static void checkApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalStateException("applicationContext未注入");
        }
    }
}

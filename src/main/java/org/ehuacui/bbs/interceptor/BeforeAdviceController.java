package org.ehuacui.bbs.interceptor;

import java.lang.annotation.*;

/**
 * 前置增强控制器
 * Created by jianwei.zhou on 2016/9/13.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface BeforeAdviceController {
    Class<? extends Interceptor>[] value();
}

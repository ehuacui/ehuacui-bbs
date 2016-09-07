package org.ehuacui.bbs.route;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ControllerBind {
    String controllerKey();

    String viewPath() default "";
}

package org.ehuacui.bbs.common;

import com.jfinal.config.Constants;
import com.jfinal.config.*;
import com.jfinal.render.FreeMarkerRender;
import org.ehuacui.bbs.route.AutoBindRoutes;
import org.ehuacui.bbs.template.PyTag;
import org.ehuacui.bbs.utils.StringUtil;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
public class AppConfig extends JFinalConfig {

    /**
     * 配置常量
     */
    public void configConstant(Constants me) {
        // 加载少量必要配置，随后可用getProperty(...)获取值
        loadPropertyFile("config.properties");
        me.setFreeMarkerViewExtension("ftl");
        String staticPath = getProperty("static.path");
        me.setBaseUploadPath(StringUtil.isBlank(staticPath) ? "static/upload" : staticPath);
        me.setMaxPostSize(1024 * 1024 * 2);
        me.setFreeMarkerTemplateUpdateDelay(300);
        me.setError401View("/WEB-INF/ftl/401.html");
        me.setError404View("/WEB-INF/ftl/404.html");
        me.setError500View("/WEB-INF/ftl/500.html");
        FreeMarkerRender.getConfiguration().setSharedVariable("py", new PyTag());
    }

    /**
     * 配置路由
     */
    public void configRoute(Routes me) {
        me.add(new AutoBindRoutes());
    }

    /**
     * 配置插件
     */
    public void configPlugin(Plugins me) {
    }

    /**
     * 配置全局拦截器
     */
    public void configInterceptor(Interceptors me) {
    }

    /**
     * 配置处理器
     */
    public void configHandler(Handlers me) {

    }

}

package org.ehuacui.bbs.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;

/**
 * Spring的监听器
 */
public class SpringContextLoaderListener extends ContextLoaderListener {

    private static final Logger logger = LoggerFactory.getLogger(SpringContextLoaderListener.class);

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("EHuaCui BBS To Start Stop");
        MDC.remove("app_name");
        super.contextDestroyed(sce);
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        //Config application name for logger
        MDC.put("app_name", "ehuacui-bbs");
        logger.info("EHuaCui BBS To Start Running");
        super.contextInitialized(event);
        logger.info("EHuaCui BBS To Completed Running");
    }
}

package org.ehuacui.listener;

import com.quanshi.report.utils.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;

/**
 * Spring的监听器
 */
public class ReportContextLoaderListener extends ContextLoaderListener {

    private static final Logger logger = LoggerFactory.getLogger(ReportContextLoaderListener.class);

    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Report Internal To Start Stop");
        MDC.remove("app_name");
    }

    public void contextInitialized(ServletContextEvent event) {
        //Config application name for logger
        MDC.put("app_name", "report-internal");
        logger.info("Report Internal  To Start Running");
        super.contextInitialized(event);
        WebUtils.setServletContext(event.getServletContext());
        logger.info("Report Internal  To Completed Running");
        // ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
    }
}

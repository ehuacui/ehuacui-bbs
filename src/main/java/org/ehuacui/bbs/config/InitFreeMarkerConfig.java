package org.ehuacui.bbs.config;

import freemarker.template.Configuration;
import org.ehuacui.bbs.template.PyTag;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

/**
 * InitFreeMarkerConfig
 * Created by Administrator on 2016/10/7.
 */
public class InitFreeMarkerConfig implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        FreeMarkerConfigurer freeMarkerConfigurer = SpringContextHolder.getBean(FreeMarkerConfigurer.class);
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        if (configuration != null) {
            configuration.setSharedVariable("py", new PyTag());
        }
    }

}

package org.ehuacui.bbs.common;

import org.ehuacui.bbs.service.ICollectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring Test
 * Created by Administrator on 2016/9/3.
 */
public class SpringTest {

    private static final Logger logger = LoggerFactory.getLogger(SpringTest.class);

    private static void te() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        ICollectService collectService = applicationContext.getBean(ICollectService.class);
        Long size = collectService.countByTid(1);
        logger.info("countByTid==={}===", size);
    }

    public static void main(String[] args) {
        te();
    }
}

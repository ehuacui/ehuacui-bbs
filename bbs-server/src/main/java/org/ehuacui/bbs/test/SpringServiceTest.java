package org.ehuacui.bbs.test;

import org.ehuacui.bbs.service.CollectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring Test
 * Created by Administrator on 2016/9/3.
 */
public class SpringServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(SpringServiceTest.class);

    private static void te() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        CollectService collectService = applicationContext.getBean(CollectService.class);
        Long size = collectService.countByTid(1);
        logger.info("countByTid==={}===", size);
    }

    public static void main(String[] args) {
        te();
    }
}

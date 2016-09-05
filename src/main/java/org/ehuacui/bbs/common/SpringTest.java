package org.ehuacui.bbs.common;

import org.ehuacui.bbs.service.ICollectService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring Test
 * Created by Administrator on 2016/9/3.
 */
public class SpringTest {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        ICollectService collectService = applicationContext.getBean(ICollectService.class);
        Long size = collectService.countByTid(1);
        System.err.println(size);
    }
}

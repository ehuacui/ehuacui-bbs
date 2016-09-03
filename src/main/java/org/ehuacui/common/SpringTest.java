package org.ehuacui.common;

import org.ehuacui.mapper.PermissionMapper;
import org.ehuacui.service.ICollectService;
import org.ehuacui.service.impl.CollectService;
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

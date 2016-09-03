package org.ehuacui.common;

import org.ehuacui.mapper.PermissionMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring Test
 * Created by Administrator on 2016/9/3.
 */
public class SpringTest {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        PermissionMapper permissionMapper = applicationContext.getBean(PermissionMapper.class);
        int size = permissionMapper.selectAllChild().size();
        System.err.println(size);
    }
}

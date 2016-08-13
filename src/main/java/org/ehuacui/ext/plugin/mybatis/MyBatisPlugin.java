package org.ehuacui.ext.plugin.mybatis;

import com.jfinal.plugin.IPlugin;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;

/**
 * MyBatis JFinal Plugin
 * Created by jianwei.zhou on 2016/8/13.
 */
public class MyBatisPlugin implements IPlugin {

    private static final Logger logger = LoggerFactory.getLogger(MyBatisPlugin.class);
    private String resource;
    private SqlSessionFactory sqlSessionFactory;
    private Reader reader;

    public MyBatisPlugin(String resource) {
        this.resource = resource;
    }

    @Override
    public boolean start() {
        try {
            reader = Resources.getResourceAsReader(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            MyBatisSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
            return true;
        } catch (Exception e) {
            logger.error("MyBatis Init Failure", e);
            return false;
        }
    }

    @Override
    public boolean stop() {
        try {
            reader.close();
            return true;
        } catch (IOException e) {
            logger.error("MyBatis Config Read Close Failure", e);
            return false;
        }
    }
}

package org.ehuacui.ext.plugin.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;

/**
 * MyBatisSqlSessionFactory
 * Created by jianwei.zhou on 2016/8/13.
 */
public class MyBatisSqlSessionFactory {
    private static SqlSessionFactory sqlSessionFactory;

    public static SqlSessionFactory getSession() {
        return sqlSessionFactory;
    }

    public static void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        MyBatisSqlSessionFactory.sqlSessionFactory = sqlSessionFactory;
    }
}

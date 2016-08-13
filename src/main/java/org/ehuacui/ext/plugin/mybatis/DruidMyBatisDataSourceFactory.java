package org.ehuacui.ext.plugin.mybatis;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;

/**
 * MyBatis DruidDataSourceFactory
 * Created by jianwei.zhou on 2016/8/13.
 */
public class DruidMyBatisDataSourceFactory extends UnpooledDataSourceFactory {

    public DruidMyBatisDataSourceFactory() {
        this.dataSource = new DruidDataSource();
    }

}

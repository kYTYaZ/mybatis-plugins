package com.fishqq.meta.mappers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import com.alibaba.dt.dataphin.meta.core.bo.datasource.JdbcDataSourceBO;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;

/**
 * create mybatis datasource by ide datasource
 *
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/9/4
 */
public class DataSourceFactory {
    public static final Map<JdbcDataSourceBO, DataSource> dataSourceMap = new ConcurrentHashMap<>();

    public static DataSource get(JdbcDataSourceBO dsBO) {
        DataSource dataSource = dataSourceMap.get(dsBO.getGuid());
        if (dataSource == null) {
            UnpooledDataSource unpooledDataSource = new UnpooledDataSource();

            unpooledDataSource.setDriver(getDriver(dsBO));
            unpooledDataSource.setUrl(dsBO.getUrl());
            unpooledDataSource.setUsername(dsBO.getUser());
            unpooledDataSource.setPassword(dsBO.getPassword());

            PooledDataSource pooledDataSource = new PooledDataSource(unpooledDataSource);
            pooledDataSource.setPoolMaximumActiveConnections(2);
            pooledDataSource.setPoolMaximumIdleConnections(2);

            dataSource = pooledDataSource;

            dataSourceMap.put(dsBO, pooledDataSource);
        }

        return dataSource;
    }

    public static String getDriver(JdbcDataSourceBO abstractJdbcDataSourceBO) {
        switch (abstractJdbcDataSourceBO.getDataSourceEnum()) {
            case POSTGRE_SQL:
                return org.postgresql.Driver.class.getCanonicalName();
            case MYSQL:
            case DRDS:
                return "com.mysql.cj.jdbc.Driver";
            case SQL_SERVER:
                return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            case ORACLE:
                return oracle.jdbc.driver.OracleDriver.class.getCanonicalName();
            case VERTICA:
                return com.vertica.jdbc.Driver.class.getCanonicalName();
            default:
                return "";
        }
    }
}

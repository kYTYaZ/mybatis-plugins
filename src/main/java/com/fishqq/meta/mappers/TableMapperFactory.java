package com.fishqq.meta.mappers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.dt.dataphin.common.mybatis.interceptor.pagination.PaginationInterceptor;
import com.alibaba.dt.dataphin.meta.core.common.mybatis.MapperFactory;
import com.alibaba.dt.dataphin.meta.core.common.mybatis.MapperLoader;
import com.alibaba.dt.dataphin.meta.debug.SqlLogInterceptor;
import com.alibaba.dt.onedata.datasource.DataSourceTypeEnum;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.Configuration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * load mapper to configuration
 *
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/9/4
 */
@Service
@Qualifier("TableMapperFactory")
public class TableMapperFactory implements MapperFactory<MetastoreTableMapper> {
    private static final String MAPPER_ROOT = "mapper/metastore";

    private Map<DataSourceTypeEnum, Configuration> configByDbType;
    private Map<DataSourceTypeEnum, Configuration> hiveConfigByDbType;


    private static String mapperPath(String name) {
        return MAPPER_ROOT + "/" + name;
    }

    public TableMapperFactory() {
        this.configByDbType = new HashMap<>();
        this.hiveConfigByDbType = new HashMap<>();

        try {
            Configuration mysql = MapperLoader.load(mapperPath("mysql-table-mapper.xml"));
            Configuration pg = MapperLoader.load(mapperPath("postgresql-table-mapper.xml"));
            Configuration vertica = MapperLoader.load(mapperPath("vertica-table-mapper.xml"));
            Configuration oracle = MapperLoader.load(mapperPath("oracle-table-mapper.xml"));
            Configuration sqlserver = MapperLoader.load(mapperPath("sqlserver-table-mapper.xml"));

            Configuration hadoopCommon = MapperLoader.load(mapperPath("hive-common-table-mapper.xml"));
            Configuration hadoopPg = MapperLoader.load(mapperPath("hive-postgresql-table-mapper.xml"));

            Interceptor pageInterceptor = new PaginationInterceptor();

            mysql.addInterceptor(pageInterceptor);
            pg.addInterceptor(pageInterceptor);
            vertica.addInterceptor(pageInterceptor);
            oracle.addInterceptor(pageInterceptor);
            sqlserver.addInterceptor(pageInterceptor);

            hadoopCommon.addInterceptor(pageInterceptor);
            hadoopPg.addInterceptor(pageInterceptor);

            SqlLogInterceptor sqlLogInterceptor = new SqlLogInterceptor();

            mysql.addInterceptor(sqlLogInterceptor);
            pg.addInterceptor(sqlLogInterceptor);
            vertica.addInterceptor(sqlLogInterceptor);
            oracle.addInterceptor(sqlLogInterceptor);
            sqlserver.addInterceptor(sqlLogInterceptor);

            hadoopCommon.addInterceptor(sqlLogInterceptor);
            hadoopPg.addInterceptor(sqlLogInterceptor);

            this.configByDbType.put(DataSourceTypeEnum.ORACLE, oracle);

            this.configByDbType.put(DataSourceTypeEnum.SQL_SERVER, sqlserver);

            this.configByDbType.put(DataSourceTypeEnum.VERTICA, vertica);

            this.configByDbType.put(DataSourceTypeEnum.POSTGRE_SQL, pg);

            this.configByDbType.put(DataSourceTypeEnum.MYSQL, mysql);
            this.configByDbType.put(DataSourceTypeEnum.DRDS, mysql);

            this.hiveConfigByDbType.put(DataSourceTypeEnum.MYSQL, hadoopCommon);
            this.hiveConfigByDbType.put(DataSourceTypeEnum.ORACLE, hadoopCommon);
            this.hiveConfigByDbType.put(DataSourceTypeEnum.SQL_SERVER, hadoopCommon);

            this.hiveConfigByDbType.put(DataSourceTypeEnum.POSTGRE_SQL, hadoopPg);
        } catch (IOException e) {
            throw new RuntimeException("can't load xml mapper file", e);
        }
    }

    @Override
    public Configuration getConfig(DataSourceTypeEnum type, DataSourceTypeEnum metaType) {
        switch (type) {
            case HIVE:
            case EMR_HIVE:
            case HADOOP:
                return this.hiveConfigByDbType.get(metaType);
            default:
                return this.configByDbType.get(type);
        }
    }

    @Override
    public Class<MetastoreTableMapper> getMapperType() {
        return MetastoreTableMapper.class;
    }
}

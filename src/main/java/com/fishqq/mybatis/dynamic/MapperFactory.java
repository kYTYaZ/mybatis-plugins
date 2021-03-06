package com.fishqq.mybatis.dynamic;

import java.util.function.Function;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

/**
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/9/5
 */
public interface MapperFactory<Type, Mapper> {
    Configuration getConfig(Type type);

    Class<Mapper> getMapperType();

    default <T> T callMapper(Type type,
                             DataSource dataSource,
                             Function<Mapper, T> api) {
        TransactionFactory trxFactory = new JdbcTransactionFactory();

        Environment env = new Environment("dev", trxFactory, dataSource);

        Configuration config = new ConfigurationWrapper(this.getConfig(type), env);

        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(config);

        SqlSession session = factory.openSession();

        try {
            Mapper mapper = session.getMapper(getMapperType());
            return api.apply(mapper);
        } finally {
            session.close();
        }
    }
}

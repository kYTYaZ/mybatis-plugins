package com.fishqq.mybatis.dynamic;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;

/**
 * load mapper definition from xml
 *
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/9/4
 */
public class MapperLoader {
    public static Configuration load(String resource) throws IOException {
        Configuration configuration = new Configuration();

        InputStream inputStream = Resources.getResourceAsStream(resource);
        XMLMapperBuilder mapperParser = new XMLMapperBuilder(
            inputStream, configuration, resource, configuration.getSqlFragments());
        mapperParser.parse();

        return configuration;
    }
}

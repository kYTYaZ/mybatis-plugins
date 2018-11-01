package com.alibaba.dt.dataphin.meta.core.service.impl;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.alibaba.dt.dataphin.meta.core.bo.DataSourceMappingBO;
import com.alibaba.dt.dataphin.meta.core.bo.datasource.JdbcDataSourceBO;
import com.alibaba.dt.dataphin.meta.core.bo.datasource.MaxComputeDataSourceBO;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/9/27
 */
public class MappingHandler {
    public static void handleTableByType(DataSourceMappingBO mappingBO,
                                         Consumer<Multimap<JdbcDataSourceBO, String>> jdbcHandler,
                                         Consumer<Multimap<MaxComputeDataSourceBO, String>> maxComputeHandler) {
        Multimap<JdbcDataSourceBO, String> jdbcMap = ArrayListMultimap.create();
        Multimap<MaxComputeDataSourceBO, String> maxComputeMap = ArrayListMultimap.create();

        mappingBO.dataSourceBOs().stream().forEach(dsBO -> {
            List<String> tableGuids = mappingBO.getDataSourceGuid2TableGuidMap().get(dsBO.getGuid());
            if (dsBO instanceof MaxComputeDataSourceBO) {
                maxComputeMap.putAll((MaxComputeDataSourceBO) dsBO, tableGuids);
            } else if (dsBO instanceof JdbcDataSourceBO) {
                jdbcMap.putAll((JdbcDataSourceBO) dsBO, tableGuids);
            }
        });

        jdbcHandler.accept(jdbcMap);
        maxComputeHandler.accept(maxComputeMap);
    }

    public static void handleByType(DataSourceMappingBO mappingBO,
                                    Consumer<List<JdbcDataSourceBO>> jdbcHandler,
                                    Consumer<List<MaxComputeDataSourceBO>> maxComputeHandler) {
        List<JdbcDataSourceBO> jdbcDataSourceBOS = mappingBO.dataSourceBOs().stream()
            .filter(dsBO -> dsBO instanceof JdbcDataSourceBO)
            .map(dsBO -> (JdbcDataSourceBO)dsBO)
            .collect(Collectors.toList());

        List<MaxComputeDataSourceBO> maxComputeDataSourceBOS = mappingBO.dataSourceBOs().stream()
            .filter(dsBO -> dsBO instanceof MaxComputeDataSourceBO)
            .map(dsBO -> (MaxComputeDataSourceBO)dsBO)
            .collect(Collectors.toList());

        jdbcHandler.accept(jdbcDataSourceBOS);
        maxComputeHandler.accept(maxComputeDataSourceBOS);
    }
}

package com.fishqq.meta.services.impl;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.alibaba.dt.dataphin.meta.client.common.ColumnQueryFilter;
import com.alibaba.dt.dataphin.meta.core.bo.ColumnBO;
import com.alibaba.dt.dataphin.meta.core.bo.datasource.JdbcDataSourceBO;
import com.alibaba.dt.dataphin.meta.core.converter.ColumnConverter;
import com.alibaba.dt.dataphin.meta.core.dal.dataobject.ColumnDO;
import com.alibaba.dt.dataphin.meta.core.dal.repository.MetastoreTableRepository;
import com.alibaba.dt.dataphin.meta.core.service.metastore.MetastoreColumnService;
import com.alibaba.dt.onedata.metaguid.GuidUtil;

import com.google.common.collect.Multimap;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/9/27
 */
@Service
@Qualifier("jdbc")
public class JdbcMetastoreColumnServiceImpl implements MetastoreColumnService<JdbcDataSourceBO> {
    @Resource
    private MetastoreTableRepository metastoreTableRepository;

    @Override
    public Map<String, List<ColumnBO>> listByTableGuid(Multimap<JdbcDataSourceBO, String> tableGuidByDsBO,
                                                       ColumnQueryFilter filter) {
        Map<String, List<ColumnBO>> result = tableGuidByDsBO.entries().stream()
            .collect(Collectors.toMap(entry -> entry.getValue(), entry -> {
                String tableName = GuidUtil.parseTableNameFromTableGuid(entry.getValue());
                List<ColumnDO> dos = this.metastoreTableRepository.listColumn(entry.getKey(), tableName);
                return ColumnConverter.toBOs(dos.stream(), tableName);
            }));

        return result;
    }

    @Override
    public Map<String, List<ColumnBO>> listByTableName(JdbcDataSourceBO dataSourceBO,
                                                       List<String> tableNames,
                                                       ColumnQueryFilter filter) {
        Map<String, List<ColumnBO>> result = tableNames.stream()
            .collect(Collectors.toMap(Function.identity(), tableName -> {
                List<ColumnDO> dos = this.metastoreTableRepository.listColumn(dataSourceBO, tableName);
                String tableGuid = GuidUtil.createTableGuid(dataSourceBO.getGuid(), tableName);
                return ColumnConverter.toBOs(dos.stream(), tableGuid);
            }));

        return result;
    }

    @Override
    public Map<String, Long> countByTableGuid(Multimap<JdbcDataSourceBO, String> tableGuidByDsBO) {
        Map<String, Long> result = tableGuidByDsBO.entries().stream()
            .collect(Collectors.toMap(entry -> entry.getValue(), entry -> {
                String tableName = GuidUtil.parseTableNameFromTableGuid(entry.getValue());
                Long count = this.metastoreTableRepository.countColumn(entry.getKey(), tableName);
                return count;
            }));

        return result;
    }

    @Override
    public Map<String, Long> countByTableName(JdbcDataSourceBO dataSourceBO,
                                              List<String> tableNames) {
        Map<String, Long> result = tableNames.stream()
            .collect(Collectors.toMap(Function.identity(), tableName -> {
                Long count = this.metastoreTableRepository.countColumn(dataSourceBO, tableName);
                return count;
            }));

        return result;
    }
}

package com.fishqq.meta.services.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.alibaba.dt.dataphin.meta.client.common.PartitionQueryFilter;
import com.alibaba.dt.dataphin.meta.client.model.column.Range;
import com.alibaba.dt.dataphin.meta.core.bo.PartitionBO;
import com.alibaba.dt.dataphin.meta.core.bo.datasource.JdbcDataSourceBO;
import com.alibaba.dt.dataphin.meta.core.converter.PartitionConverter;
import com.alibaba.dt.dataphin.meta.core.dal.dataobject.PartitionDO;
import com.alibaba.dt.dataphin.meta.core.dal.repository.MetastoreTableRepository;
import com.alibaba.dt.dataphin.meta.core.service.metastore.MetastorePartitionService;
import com.alibaba.dt.onedata.metaguid.GuidUtil;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/9/27
 */
@Service
@Qualifier("jdbc")
public class JdbcMetastorePartitionServiceImpl implements MetastorePartitionService<JdbcDataSourceBO> {
    @Resource
    private MetastoreTableRepository metastoreTableRepository;

    @Override
    public List<PartitionBO> listByTableGuid(Multimap<JdbcDataSourceBO, String> tableGuidByDsBO,
                                             PartitionQueryFilter filter) {
        List<PartitionBO> result = tableGuidByDsBO.entries().stream()
            .flatMap(entry -> {
                String tableName = GuidUtil.parseTableNameFromTableGuid(entry.getValue());
                List<PartitionDO> dos = this.metastoreTableRepository.listPartitionByPage(
                    entry.getKey(), tableName, filter.getPaginationCriteria());

                return PartitionConverter.toBOs(dos, entry.getValue()).stream();
            })
            .collect(Collectors.toList());

        return result;
    }

    @Override
    public List<PartitionBO> listByTableName(JdbcDataSourceBO dataSourceBO,
                                             List<String> tableNames,
                                             PartitionQueryFilter filter) {
        Multimap<JdbcDataSourceBO, String> tableGuidByDsBO = ArrayListMultimap.create();

        tableNames.stream().forEach(tableName -> {
            tableGuidByDsBO.put(dataSourceBO, GuidUtil.createTableGuid(dataSourceBO.getGuid(), tableName));
        });

        return this.listByTableGuid(tableGuidByDsBO, filter);
    }

    @Override
    public List<PartitionBO> listInRange(Multimap<JdbcDataSourceBO, String> tableGuidByDsBO,
                                         Multimap<String, Range<String>> rangesByTableGuid,
                                         PartitionQueryFilter filter) {
        List<PartitionBO> result = tableGuidByDsBO.entries().stream().flatMap(entry -> {
            JdbcDataSourceBO dsBO = entry.getKey();
            String tableGuid = entry.getValue();
            String tableName = GuidUtil.parseTableNameFromTableGuid(tableGuid);

            List<Range<String>> ranges = rangesByTableGuid.get(tableGuid).stream().collect(Collectors.toList());

            List<PartitionDO> dos = this.metastoreTableRepository.listPartitionByRange(dsBO, tableName, ranges);

            return PartitionConverter.toBOs(dos, tableGuid).stream();
        }).collect(Collectors.toList());

        return result;
    }

    @Override
    public Map<String, Long> countByTableGuid(Multimap<JdbcDataSourceBO, String> tableGuidByDsBO) {
        Map<String, Long> result = tableGuidByDsBO.entries().stream()
            .collect(Collectors.toMap(entry -> entry.getValue(), entry -> {
                String tableName = GuidUtil.parseTableNameFromTableGuid(entry.getValue());
                Long count = this.metastoreTableRepository.countPartition(entry.getKey(), tableName);
                return count;
            }));

        return result;
    }

    @Override
    public Map<String, Long> countByTableName(JdbcDataSourceBO dataSourceBO,
                                              List<String> tableNames) {
        Multimap<JdbcDataSourceBO, String> tableGuidByDsBO = ArrayListMultimap.create();

        tableNames.stream().forEach(tableName -> {
            tableGuidByDsBO.put(dataSourceBO, GuidUtil.createTableGuid(dataSourceBO.getGuid(), tableName));
        });

        return this.countByTableGuid(tableGuidByDsBO);
    }

    @Override
    public List<PartitionBO> getMinMax(JdbcDataSourceBO dataSourceBO, String tableName) {
        List<PartitionDO> dos = this.metastoreTableRepository.getMinMaxPartition(dataSourceBO, tableName);

        return PartitionConverter.toBOs(dos, GuidUtil.createTableGuid(dataSourceBO.getGuid(), tableName));
    }
}

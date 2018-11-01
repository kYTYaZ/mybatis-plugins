package com.alibaba.dt.dataphin.meta.core.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.alibaba.dt.dataphin.meta.client.common.PaginatedResult;
import com.alibaba.dt.dataphin.meta.client.common.PartitionQueryFilter;
import com.alibaba.dt.dataphin.meta.client.model.column.Range;
import com.alibaba.dt.dataphin.meta.core.bo.DataSourceMappingBO;
import com.alibaba.dt.dataphin.meta.core.bo.PartitionBO;
import com.alibaba.dt.dataphin.meta.core.bo.datasource.DataSourceBO;
import com.alibaba.dt.dataphin.meta.core.bo.datasource.JdbcDataSourceBO;
import com.alibaba.dt.dataphin.meta.core.bo.datasource.MaxComputeDataSourceBO;
import com.alibaba.dt.dataphin.meta.core.service.PartitionService;
import com.alibaba.dt.dataphin.meta.core.service.datasource.DataSourceMappingService;
import com.alibaba.dt.dataphin.meta.core.service.metastore.MetastorePartitionService;
import com.alibaba.dt.dataphin.meta.core.service.synced.SyncedPartitionService;
import com.alibaba.dt.onedata.metaguid.GuidUtil;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/9/14
 */
@Service
public class PartitionServiceImpl implements PartitionService {
    @Resource
    private DataSourceMappingService mappingService;

    @Resource
    private SyncedPartitionService syncedPartitionService;

    @Resource
    @Qualifier("jdbc")
    private MetastorePartitionService<JdbcDataSourceBO> jdbcPartitionService;

    @Resource
    @Qualifier("maxCompute")
    private MetastorePartitionService<MaxComputeDataSourceBO> maxComputePartitionService;

    @Override
    public PaginatedResult<PartitionBO> listByTableGuid(List<String> tableGuids, PartitionQueryFilter filter) {
        if (filter.isNeedRealTime()) {
            DataSourceMappingBO mappingBO = this.mappingService.getByTableGuid(
                filter.getTenantId(), tableGuids, filter.isNeedRealTime());

            return this.handleByMapping(mappingBO, filter);
        } else {
            this.mappingService.checkTenantByDataSourceGuid(filter.getTenantId(), tableGuids);

            return this.syncedPartitionService.listByTableGuid(tableGuids, filter);
        }
    }

    @Override
    public PaginatedResult<PartitionBO> listByTableName(Long dataSourceId,
                                                        List<String> tableNames,
                                                        PartitionQueryFilter filter) {
        if (filter.isNeedRealTime()) {
            DataSourceMappingBO mappingBO = this.mappingService.getByTableName(
                filter.getTenantId(), Arrays.asList(dataSourceId), tableNames, filter.isNeedRealTime());

            return this.handleByMapping(mappingBO, filter);
        } else {
            String dsGuid = this.mappingService.getDataSourceGuid(filter.getTenantId(), dataSourceId);

            List<String> tableGuids = tableNames.stream()
                .map(tableName -> GuidUtil.createTableGuid(dsGuid, tableName))
                .collect(Collectors.toList());

            PaginatedResult<PartitionBO> bos = this.syncedPartitionService.listByTableGuid(tableGuids, filter);

            return bos;
        }
    }

    private PaginatedResult<PartitionBO> handleByMapping(DataSourceMappingBO mappingBO, PartitionQueryFilter filter) {
        List<PartitionBO> result = Lists.newArrayList();

        MappingHandler.handleTableByType(mappingBO,
            jdbcMap -> result.addAll(this.jdbcPartitionService.listByTableGuid(jdbcMap, filter)),
            maxComputeMap -> result.addAll(this.maxComputePartitionService.listByTableGuid(maxComputeMap, filter)));

        return new PaginatedResult<>(result.size(), result);
    }

    @Override
    public List<PartitionBO> listInRange(Multimap<String, Range<String>> rangesByTableGuid,
                                         PartitionQueryFilter filter) {
        if (filter.isNeedRealTime()) {
            List<String> tableGuids = rangesByTableGuid.keySet().stream().collect(Collectors.toList());

            DataSourceMappingBO mappingBO = this.mappingService.getByTableGuid(
                filter.getTenantId(), tableGuids, filter.isNeedRealTime());

            List<PartitionBO> result = Lists.newArrayList();

            MappingHandler.handleTableByType(mappingBO,
                jdbcMap -> {
                    Multimap<String, Range<String>> jdbcRanges = ArrayListMultimap.create();
                    jdbcMap.values().stream().forEach(
                        jdbcTableGuid -> jdbcRanges.putAll(jdbcTableGuid, rangesByTableGuid.get(jdbcTableGuid)));
                    result.addAll(this.jdbcPartitionService.listInRange(jdbcMap, jdbcRanges, filter));
                },
                maxComputeMap -> {
                    Multimap<String, Range<String>> maxComputeRanges = ArrayListMultimap.create();
                    maxComputeMap.values().stream().forEach(maxComputeGuid -> maxComputeRanges
                        .putAll(maxComputeGuid, rangesByTableGuid.get(maxComputeGuid)));
                    result.addAll(this.maxComputePartitionService.listInRange(maxComputeMap, maxComputeRanges, filter));
                });

            return result;
        } else {
            List<String> dsGuids = rangesByTableGuid.keySet().stream()
                .map(GuidUtil::parseDataSourceGuidFromTableGuid)
                .distinct()
                .collect(Collectors.toList());

            this.mappingService.checkTenantByDataSourceGuid(filter.getTenantId(), dsGuids);

            List<PartitionBO> bos = this.syncedPartitionService.listInRange(rangesByTableGuid, filter);

            return bos;
        }
    }

    @Override
    public Map<String, Long> countByTableGuid(Long tenantId, List<String> tableGuids, Boolean isRealTime) {
        if (isRealTime) {
            DataSourceMappingBO mappingBO = this.mappingService.getByTableGuid(tenantId, tableGuids, true);

            Map<String, Long> result = Maps.newHashMap();
            MappingHandler.handleTableByType(mappingBO,
                jdbcMap -> result.putAll(this.jdbcPartitionService.countByTableGuid(jdbcMap)),
                maxComputeMap -> result.putAll(this.maxComputePartitionService.countByTableGuid(maxComputeMap)));

            return result;
        }

        List<String> dsGuids = tableGuids.stream()
            .map(GuidUtil::parseDataSourceGuidFromTableGuid)
            .distinct()
            .collect(Collectors.toList());

        this.mappingService.checkTenantByDataSourceGuid(tenantId, dsGuids);

        return this.syncedPartitionService.countByTableGuid(tableGuids);
    }

    @Override
    public Map<String, Long> countByTableName(Long tenantId,
                                              Long dataSourceId,
                                              List<String> tableNames,
                                              Boolean isRealTime) {
        if (isRealTime) {
            DataSourceMappingBO mappingBO = this.mappingService.getByTableName(
                tenantId, Arrays.asList(dataSourceId), tableNames, isRealTime);

            Map<String, Long> result = Maps.newHashMap();

            MappingHandler.handleTableByType(mappingBO,
                jdbcMap -> result.putAll(this.jdbcPartitionService.countByTableGuid(jdbcMap)),
                maxComputeMap -> result.putAll(this.maxComputePartitionService.countByTableGuid(maxComputeMap)));

            return result.entrySet().stream().collect(Collectors.toMap(
                entry -> GuidUtil.parseTableNameFromTableGuid(entry.getKey()),
                entry -> entry.getValue()
            ));
        }

        String dsGuid = this.mappingService.getDataSourceGuid(tenantId, dataSourceId);

        List<String> tableGuids = tableNames.stream()
            .map(tableName -> GuidUtil.createTableGuid(dsGuid, tableName))
            .collect(Collectors.toList());

        return this.syncedPartitionService.countByTableGuid(tableGuids);
    }

    @Override
    public List<PartitionBO> getMinMax(String tableGuid, PartitionQueryFilter filter) {
        if (filter.isNeedRealTime()) {
            DataSourceBO dataSourceBO = this.mappingService.getByTableGuid(
                filter.getTenantId(), Arrays.asList(tableGuid), true).dataSourceBOs().get(0);

            String tableName = GuidUtil.parseTableNameFromTableGuid(tableGuid);

            if (dataSourceBO instanceof JdbcDataSourceBO) {
                return this.jdbcPartitionService.getMinMax((JdbcDataSourceBO) dataSourceBO, tableName);
            } else {
                return this.maxComputePartitionService.getMinMax((MaxComputeDataSourceBO) dataSourceBO, tableName);
            }
        }

        this.mappingService.checkTenantByDataSourceGuid(filter.getTenantId(), Arrays.asList(tableGuid));

        List<PartitionBO> bos = this.syncedPartitionService.getMinMax(tableGuid, filter);

        return bos;
    }
}

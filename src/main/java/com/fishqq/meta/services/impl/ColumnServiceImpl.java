package com.alibaba.dt.dataphin.meta.core.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.alibaba.dt.dataphin.meta.client.common.ColumnQueryFilter;
import com.alibaba.dt.dataphin.meta.core.bo.ColumnBO;
import com.alibaba.dt.dataphin.meta.core.bo.DataSourceMappingBO;
import com.alibaba.dt.dataphin.meta.core.bo.datasource.JdbcDataSourceBO;
import com.alibaba.dt.dataphin.meta.core.bo.datasource.MaxComputeDataSourceBO;
import com.alibaba.dt.dataphin.meta.core.service.ColumnService;
import com.alibaba.dt.dataphin.meta.core.service.datasource.DataSourceMappingService;
import com.alibaba.dt.dataphin.meta.core.service.metastore.MetastoreColumnService;
import com.alibaba.dt.dataphin.meta.core.service.synced.SyncedColumnService;
import com.alibaba.dt.onedata.metaguid.GuidUtil;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/9/14
 */
@Service
public class ColumnServiceImpl implements ColumnService {
    @Resource
    private DataSourceMappingService dataSourceService;

    @Resource
    private SyncedColumnService syncedColumnService;

    @Resource
    @Qualifier("jdbc")
    private MetastoreColumnService<JdbcDataSourceBO> jdbcColumnService;

    @Resource
    @Qualifier("maxCompute")
    private MetastoreColumnService<MaxComputeDataSourceBO> maxComputeColumnService;

    @Override
    public Map<String, List<ColumnBO>> listByTableGuid(List<String> tableGuids, ColumnQueryFilter filter) {
        DataSourceMappingBO mappingBO = this.dataSourceService.getByTableGuid(
            filter.getTenantId(), tableGuids, filter.isNeedRealTime());

        return this.listByMapping(mappingBO, true, filter);
    }

    @Override
    public Map<String, List<ColumnBO>> listByTableName(Long dataSourceId,
                                                       List<String> tableNames,
                                                       ColumnQueryFilter filter) {
        DataSourceMappingBO mappingBO = this.dataSourceService.getByTableName(
            filter.getTenantId(), Arrays.asList(dataSourceId), tableNames, filter.isNeedRealTime());

        return this.listByMapping(mappingBO, false, filter);
    }

    private Map<String, List<ColumnBO>> listByMapping(DataSourceMappingBO mappingBO, boolean byGuid, ColumnQueryFilter filter) {
        if (filter.isNeedRealTime()) {
            Map<String, List<ColumnBO>> result = Maps.newHashMap();

            MappingHandler.handleTableByType(mappingBO,
                jdbcMap -> result.putAll(this.jdbcColumnService.listByTableGuid(jdbcMap, filter)),
                maxComputeMap -> result.putAll(this.maxComputeColumnService.listByTableGuid(maxComputeMap, filter)));

            if (byGuid) {
                return result;
            } else {
                return result.entrySet().stream().collect(Collectors.toMap(
                    entry -> GuidUtil.parseTableNameFromTableGuid(entry.getKey()),
                    entry -> entry.getValue()
                ));
            }
        } else {
            if (mappingBO.hasModel()) {
                Map<String, List<ColumnBO>> result = this.syncedColumnService.listByTableGuid(
                    mappingBO.modelTableGuids(), filter);

                if (byGuid) {
                    return result;
                } else {
                    return result.entrySet().stream().collect(Collectors.toMap(
                        entry -> GuidUtil.parseTableNameFromTableGuid(entry.getKey()),
                        entry -> entry.getValue()
                    ));
                }
            } else if (mappingBO.hasPhysical()) {
                Map<String, List<ColumnBO>> columnByTableGuid =
                    this.jdbcColumnService.listByTableGuid(mappingBO.physicalDataSourceBO2TableGuidMap(), filter);

                if (byGuid) {
                    return columnByTableGuid;
                } else {
                    return columnByTableGuid.entrySet().stream().collect(Collectors.toMap(
                        entry -> GuidUtil.parseTableNameFromTableGuid(entry.getKey()),
                        entry -> entry.getValue()
                    ));
                }
            } else {
                return Maps.newHashMap();
            }
        }
    }

    @Override
    public Map<String, Long> countByTableGuid(Long tenantId, List<String> tableGuids, Boolean isRealTime) {
        DataSourceMappingBO mappingBO = this.dataSourceService.getByTableGuid(tenantId, tableGuids, isRealTime);

        return this.countByMapping(mappingBO, true, isRealTime);
    }

    @Override
    public Map<String, Long> countByTableName(Long tenantId,
                                              Long dataSourceId,
                                              List<String> tableNames,
                                              Boolean isRealTime) {
        DataSourceMappingBO mappingBO = this.dataSourceService.getByTableName(
            tenantId, Arrays.asList(dataSourceId), tableNames, isRealTime);

        return this.countByMapping(mappingBO, false, isRealTime);
    }

    private Map<String, Long> countByMapping(DataSourceMappingBO mappingBO, boolean byGuid, boolean isRealTime) {
        if (isRealTime) {
            Map<String, Long> result = Maps.newHashMap();

            MappingHandler.handleTableByType(mappingBO,
                jdbcMap -> result.putAll(this.jdbcColumnService.countByTableGuid(jdbcMap)),
                maxComputeMap -> result.putAll(this.maxComputeColumnService.countByTableGuid(maxComputeMap)));

            if (byGuid) {
                return result;
            } else {
                return result.entrySet().stream().collect(Collectors.toMap(
                    entry -> GuidUtil.parseTableNameFromTableGuid(entry.getKey()),
                    entry -> entry.getValue()
                ));
            }
        }

        if (mappingBO.hasModel()) {
            Map<String, Long> result = this.syncedColumnService.countByTableGuid(mappingBO.modelTableGuids());
            if (byGuid) {
                return result;
            } else {
                return result.entrySet().stream().collect(Collectors.toMap(
                    entry -> GuidUtil.parseTableNameFromTableGuid(entry.getKey()),
                    entry -> entry.getValue()
                ));
            }
        } else if (mappingBO.hasPhysical()) {
            Map<String, Long> countByTableGuid =
                this.jdbcColumnService.countByTableGuid(mappingBO.physicalDataSourceBO2TableGuidMap());

            if (byGuid) {
                return countByTableGuid;
            } else {
                return countByTableGuid.entrySet().stream().collect(Collectors.toMap(
                    entry -> GuidUtil.parseTableNameFromTableGuid(entry.getKey()),
                    entry -> entry.getValue()
                ));
            }
        } else {
            return Maps.newHashMap();
        }
    }
}

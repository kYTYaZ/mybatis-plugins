package com.alibaba.dt.dataphin.meta.server.rpc;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.dt.dataphin.common.lang.result.ResultDTO;
import com.alibaba.dt.dataphin.meta.client.api.PartitionApi;
import com.alibaba.dt.dataphin.meta.client.common.PaginatedResult;
import com.alibaba.dt.dataphin.meta.client.common.PartitionQueryFilter;
import com.alibaba.dt.dataphin.meta.client.model.column.Range;
import com.alibaba.dt.dataphin.meta.client.model.partition.Partition;
import com.alibaba.dt.dataphin.meta.core.bo.PartitionBO;
import com.alibaba.dt.dataphin.meta.core.converter.PartitionConverter;
import com.alibaba.dt.dataphin.meta.core.service.PartitionService;
import com.alibaba.dt.dataphin.meta.server.ApiLogger;
import com.alibaba.dt.onedata.rpc.annotation.MethodMapping;
import com.alibaba.dt.onedata.rpc.annotation.ServiceProvider;

import com.google.common.collect.Multimap;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/9/13
 */
@Service
@Qualifier("rpc")
@ServiceProvider
public class RpcPartitionApi extends ApiLogger implements PartitionApi {
    @Resource
    private PartitionService partitionService;

    @Override
    @MethodMapping
    public ResultDTO<PaginatedResult<Partition>> listByTableGuid(List<String> tableGuids, PartitionQueryFilter filter) {
        return this.log(() -> {
                PaginatedResult<PartitionBO> bos = this.partitionService.listByTableGuid(tableGuids, filter);
                return new PaginatedResult<>(bos.getRecordCount(),
                    PartitionConverter.toPartitions(bos.getData(), filter));
            }, "listByTableGuid", tableGuids);
    }

    @Override
    @MethodMapping
    public ResultDTO<PaginatedResult<Partition>> listByTableGuid(String tableGuid, PartitionQueryFilter filter) {
        return PartitionApi.super.listByTableGuid(tableGuid, filter);
    }

    @Override
    @MethodMapping
    public ResultDTO<PaginatedResult<Partition>> listByTableName(Long dataSourceId,
                                                                 List<String> tableNames,
                                                                 PartitionQueryFilter filter) {
        return this.log(() -> {
                PaginatedResult<PartitionBO> bos = this.partitionService.listByTableName(
                    dataSourceId, tableNames, filter);
                return new PaginatedResult<>(bos.getRecordCount(),
                    PartitionConverter.toPartitions(bos.getData(), filter));
            }, "listByTableName", dataSourceId, tableNames);
    }

    @Override
    @MethodMapping
    public ResultDTO<PaginatedResult<Partition>> listByTableName(Long dataSourceId,
                                                                 String tableName,
                                                                 PartitionQueryFilter filter) {
        return PartitionApi.super.listByTableName(dataSourceId, tableName, filter);
    }

    @Override
    @MethodMapping
    public ResultDTO<List<Partition>> listInRange(String tableGuid, Range<String> range, PartitionQueryFilter filter) {
        return PartitionApi.super.listInRange(tableGuid, range, filter);
    }

    @Override
    public ResultDTO<List<Partition>> listInRange(Multimap<String, Range<String>> rangesByTableGuid,
                                                  PartitionQueryFilter filter) {
        return this.log(() -> {
            List<PartitionBO> bos = this.partitionService.listInRange(rangesByTableGuid, filter);
            return PartitionConverter.toPartitions(bos, filter);
        }, "listInRange", rangesByTableGuid);
    }

    @Override
    @MethodMapping
    public ResultDTO<List<Partition>> getMinMax(String tableGuid, PartitionQueryFilter filter) {
        return this.log(() -> {
            List<PartitionBO> bos = this.partitionService.getMinMax(tableGuid, filter);
            return PartitionConverter.toPartitions(bos, filter);
        }, "getMinMax", tableGuid);
    }

    @Override
    @MethodMapping
    public ResultDTO<Map<String, Long>> countByTableGuid(Long tenantId, List<String> tableGuids, Boolean isRealTime) {
        return this.log(() -> this.partitionService.countByTableGuid(tenantId, tableGuids, isRealTime),
            "countByTableGuid", tenantId, tableGuids, isRealTime);
    }

    @Override
    @MethodMapping
    public ResultDTO<Long> countByTableGuid(Long tenantId, String tableGuid, Boolean isRealTime) {
        return PartitionApi.super.countByTableGuid(tenantId, tableGuid, isRealTime);
    }

    @Override
    @MethodMapping
    public ResultDTO<Map<String, Long>> countByTableName(Long tenantId,
                                                         Long dataSourceId,
                                                         List<String> tableNames,
                                                         Boolean isRealTime) {
        return this.log(() -> this.partitionService.countByTableName(tenantId, dataSourceId, tableNames, isRealTime),
            "countByTableName", tenantId, dataSourceId, tableNames, isRealTime);
    }

    @Override
    @MethodMapping
    public ResultDTO<Long> countByTableName(Long tenantId, Long dataSourceId, String tableName, Boolean isRealTime) {
        return PartitionApi.super.countByTableName(tenantId, dataSourceId, tableName, isRealTime);
    }
}

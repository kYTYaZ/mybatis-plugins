package com.alibaba.dt.dataphin.meta.server.rpc;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.alibaba.dt.dataphin.common.lang.result.ResultDTO;
import com.alibaba.dt.dataphin.meta.client.api.ColumnApi;
import com.alibaba.dt.dataphin.meta.client.common.ColumnQueryFilter;
import com.alibaba.dt.dataphin.meta.client.model.column.Column;
import com.alibaba.dt.dataphin.meta.core.bo.ColumnBO;
import com.alibaba.dt.dataphin.meta.core.converter.ColumnConverter;
import com.alibaba.dt.dataphin.meta.core.service.ColumnService;
import com.alibaba.dt.dataphin.meta.server.ApiLogger;
import com.alibaba.dt.onedata.rpc.annotation.MethodMapping;
import com.alibaba.dt.onedata.rpc.annotation.ServiceProvider;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * 白路 bailu.zjj@alibaba-inc.com
 * 2018/9/13
 */
@Service
@Qualifier("rpc")
@ServiceProvider
public class RpcColumnApi extends ApiLogger implements ColumnApi {
    @Resource
    private ColumnService columnService;

    @Override
    @MethodMapping
    public ResultDTO<Map<String, List<Column>>> listByTableGuid(List<String> tableGuids, ColumnQueryFilter filter) {
        return log(() -> toColumn(columnService.listByTableGuid(tableGuids, filter), filter),
            "listByTableGuid", tableGuids);
    }

    @Override
    @MethodMapping
    public ResultDTO<List<Column>> listByTableGuid(String tableGuid, ColumnQueryFilter filter) {
        return ColumnApi.super.listByTableGuid(tableGuid, filter);
    }

    @Override
    @MethodMapping
    public ResultDTO<Map<String, List<Column>>> listByTableName(Long dataSourceId,
                                                                List<String> tableNames,
                                                                ColumnQueryFilter filter) {
        return log(() -> toColumn(columnService.listByTableName(dataSourceId, tableNames, filter), filter),
            "listByTableName", dataSourceId, tableNames);
    }

    @Override
    @MethodMapping
    public ResultDTO<List<Column>> listByTableName(Long dataSourceId, String tableName, ColumnQueryFilter filter) {
        return ColumnApi.super.listByTableName(dataSourceId, tableName, filter);
    }

    @Override
    @MethodMapping
    public ResultDTO<Map<String, Long>> countByTableGuid(Long tenantId, List<String> tableGuids, Boolean isRealTime) {
        return log(() -> columnService.countByTableGuid(tenantId, tableGuids, isRealTime),
            "countByTableGuid", tenantId, tableGuids, isRealTime);
    }

    @Override
    @MethodMapping
    public ResultDTO<Long> countByTableGuid(Long tenantId, String tableGuid, Boolean isRealTime) {
        return ColumnApi.super.countByTableGuid(tenantId, tableGuid, isRealTime);
    }

    @Override
    @MethodMapping
    public ResultDTO<Map<String, Long>> countByTableName(Long tenantId,
                                                         Long dataSourceId,
                                                         List<String> tableNames,
                                                         Boolean isRealTime) {
        return log(() -> columnService.countByTableName(tenantId, dataSourceId, tableNames, isRealTime),
            "countByTableName", tenantId, dataSourceId, tableNames, isRealTime);
    }

    @Override
    @MethodMapping
    public ResultDTO<Long> countByTableName(Long tenantId, Long dataSourceId, String tableName, Boolean isRealTime) {
        return ColumnApi.super.countByTableName(tenantId, dataSourceId, tableName, isRealTime);
    }

    private Map<String, List<Column>> toColumn(Map<String, List<ColumnBO>> columnBOMap, ColumnQueryFilter filter) {
        return columnBOMap.keySet().stream().collect(Collectors.toMap(
            Function.identity(),
            key -> ColumnConverter.toColumns(columnBOMap.get(key), filter.isNeedExt(), filter.isNeedStat())));
    }
}


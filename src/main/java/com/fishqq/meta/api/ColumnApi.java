package com.fishqq.meta.api;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.fishqq.meta.api.common.ColumnQueryFilter;
import com.fishqq.meta.api.model.column.Column;

/**
 * 字段api
 *
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/9/13
 */
public interface ColumnApi {
    ResultDTO<Map<String, List<Column>>> listByTableGuid(List<String> tableGuids,
                                                         ColumnQueryFilter filter);

    ResultDTO<Map<String, List<Column>>> listByTableName(Long dataSourceId,
                                                         List<String> tableNames,
                                                         ColumnQueryFilter filter);

    default ResultDTO<List<Column>> listByTableGuid(String tableGuid,
                                                    ColumnQueryFilter filter) {
        ResultDTO<Map<String, List<Column>>> result = this.listByTableGuid(Arrays.asList(tableGuid), filter);

        return ResultUtil.fromMap(result, tableGuid);
    }

    default ResultDTO<List<Column>> listByTableName(Long dataSourceId,
                                                    String tableName,
                                                    ColumnQueryFilter filter) {
        ResultDTO<Map<String, List<Column>>> result = this.listByTableName(
            dataSourceId, Arrays.asList(tableName), filter);

        return ResultUtil.fromMap(result, tableName);
    }

    ResultDTO<Map<String, Long>> countByTableGuid(Long tenantId,
                                                  List<String> tableGuids,
                                                  Boolean isRealTime);

    default ResultDTO<Long> countByTableGuid(Long tenantId,
                                             String tableGuid,
                                             Boolean isRealTime) {
        ResultDTO<Map<String, Long>> result = this.countByTableGuid(tenantId, Arrays.asList(tableGuid), isRealTime);

        return ResultUtil.fromMap(result, tableGuid);
    }

    ResultDTO<Map<String, Long>> countByTableName(Long tenantId,
                                                  Long dataSourceId,
                                                  List<String> tableNames,
                                                  Boolean isRealTime);

    default ResultDTO<Long> countByTableName(Long tenantId,
                                             Long dataSourceId,
                                             String tableName,
                                             Boolean isRealTime) {
        ResultDTO<Map<String, Long>> result = this.countByTableName(
            tenantId, dataSourceId, Arrays.asList(tableName), isRealTime);

        return ResultUtil.fromMap(result, tableName);
    }
}

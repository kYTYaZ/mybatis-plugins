package com.fishqq.meta.api;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.alibaba.dt.dataphin.common.lang.criteria.OrderCriteria;
import com.alibaba.dt.dataphin.common.lang.order.OrderEnum;
import com.alibaba.dt.dataphin.common.lang.result.ResultDTO;
import com.alibaba.dt.dataphin.meta.client.common.PaginatedResult;
import com.alibaba.dt.dataphin.meta.client.common.PartitionQueryFilter;
import com.alibaba.dt.dataphin.meta.client.model.column.Range;
import com.alibaba.dt.dataphin.meta.client.model.partition.Partition;
import com.alibaba.dt.dataphin.meta.client.util.ResultUtil;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * 分区api
 *
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/9/13
 */
public interface PartitionApi {
    OrderCriteria DEFAULT_ORDER = OrderCriteria.builder().orderBy("gmtCreate").order(OrderEnum.DESC).build();

    ResultDTO<PaginatedResult<Partition>> listByTableGuid(List<String> tableGuids,
                                                          PartitionQueryFilter filter);

    default ResultDTO<PaginatedResult<Partition>> listByTableGuid(String tableGuid,
                                                                  PartitionQueryFilter filter) {
        return this.listByTableGuid(Arrays.asList(tableGuid), filter);
    }

    ResultDTO<PaginatedResult<Partition>> listByTableName(Long dataSourceId,
                                                          List<String> tableNames,
                                                          PartitionQueryFilter filter);

    default ResultDTO<PaginatedResult<Partition>> listByTableName(Long dataSourceId,
                                                                  String tableName,
                                                                  PartitionQueryFilter filter) {
        return this.listByTableName(dataSourceId, Arrays.asList(tableName), filter);
    }

    ResultDTO<List<Partition>> listInRange(Multimap<String, Range<String>> rangesByTableGuid,
                                           PartitionQueryFilter filter);

    default ResultDTO<List<Partition>> listInRange(String tableGuid,
                                                   List<Range<String>> ranges,
                                                   PartitionQueryFilter filter) {
        Multimap<String, Range<String>> rangesByTableGuid = ArrayListMultimap.create();

        rangesByTableGuid.putAll(tableGuid, ranges);

        return this.listInRange(rangesByTableGuid, filter);
    }

    default ResultDTO<List<Partition>> listInRange(String tableGuid,
                                                   Range<String> range,
                                                   PartitionQueryFilter filter) {
        return this.listInRange(tableGuid, Arrays.asList(range), filter);
    }

    ResultDTO<List<Partition>> getMinMax(String tableGuid,
                                         PartitionQueryFilter filter);

    ResultDTO<Map<String, Long>> countByTableGuid(Long tenantId,
                                                  List<String> tableGuids,
                                                  Boolean isRealTime);

    default ResultDTO<Long> countByTableGuid(Long tenantId,
                                             String tableGuid,
                                             Boolean isRealTime) {
        ResultDTO<Map<String, Long>> result = this.countByTableGuid(tenantId, Arrays.asList(tableGuid), isRealTime);

        if (result.isSuccess()) {
            return ResultUtil.ok(result.getData().get(tableGuid));
        } else {
            return ResultUtil.error(result.getErrorCode(), result.getMessageArguments());
        }
    }

    ResultDTO<Map<String, Long>> countByTableName(Long tenantId,
                                                  Long dataSourceId, List<String> tableNames,
                                                  Boolean isRealTime);

    default ResultDTO<Long> countByTableName(Long tenantId,
                                             Long dataSourceId,
                                             String tableName,
                                             Boolean isRealTime) {
        ResultDTO<Map<String, Long>> result = this.countByTableName(
            tenantId, dataSourceId, Arrays.asList(tableName), isRealTime);

        if (result.isSuccess()) {
            return ResultUtil.ok(result.getData().get(tableName));
        } else {
            return ResultUtil.error(result.getErrorCode(), result.getMessageArguments());
        }
    }
}

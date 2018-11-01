package com.alibaba.dt.dataphin.meta.core.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.alibaba.dt.dataphin.meta.client.common.PaginatedResult;
import com.alibaba.dt.dataphin.meta.client.common.PartitionQueryFilter;
import com.alibaba.dt.dataphin.meta.client.model.column.Range;
import com.alibaba.dt.dataphin.meta.core.bo.PartitionBO;

import com.google.common.collect.Multimap;

/**
 * 分区service
 *
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/9/13
 */
public interface PartitionService {
    PaginatedResult<PartitionBO> listByTableGuid(@NotNull List<String> tableGuids,
                                                 @NotNull PartitionQueryFilter filter);

    default PaginatedResult<PartitionBO> listByTableGuid(@NotNull String tableGuid,
                                                         @NotNull PartitionQueryFilter filter) {
        return this.listByTableGuid(Arrays.asList(tableGuid), filter);
    }

    PaginatedResult<PartitionBO> listByTableName(@NotNull Long dataSourceId,
                                                 @NotNull List<String> tableNames,
                                                 @NotNull PartitionQueryFilter filter);

    List<PartitionBO> listInRange(@NotNull Multimap<String, Range<String>> rangesByTableGuid,
                                  @NotNull PartitionQueryFilter filter);

    Map<String, Long> countByTableGuid(@NotNull Long tenantId,
                                       @NotNull List<String> tableGuids,
                                       @NotNull Boolean isRealTime);

    Map<String, Long> countByTableName(@NotNull Long tenantId,
                                       @NotNull Long dataSourceId, List<String> tableNames,
                                       @NotNull Boolean isRealTime);

    List<PartitionBO> getMinMax(@NotNull String tableGuid,
                                @NotNull PartitionQueryFilter filter);
}

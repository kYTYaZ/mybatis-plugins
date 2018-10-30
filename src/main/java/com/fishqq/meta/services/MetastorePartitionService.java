package com.fishqq.meta.services;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.alibaba.dt.dataphin.meta.client.common.PartitionQueryFilter;
import com.alibaba.dt.dataphin.meta.client.model.column.Range;
import com.alibaba.dt.dataphin.meta.core.bo.PartitionBO;
import com.alibaba.dt.dataphin.meta.core.bo.datasource.DataSourceBO;

import com.google.common.collect.Multimap;

/**
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/9/27
 */
public interface MetastorePartitionService<T extends DataSourceBO> {
    List<PartitionBO> listByTableGuid(@NotNull Multimap<T, String> tableGuidByDsBO,
                                      @NotNull PartitionQueryFilter filter);

    List<PartitionBO> listByTableName(@NotNull T dataSourceBO,
                                      @NotNull List<String> tableNames,
                                      @NotNull PartitionQueryFilter filter);

    List<PartitionBO> listInRange(Multimap<T, String> tableGuidByDsBO,
                                  @NotNull Multimap<String, Range<String>> rangesByTableGuid,
                                  @NotNull PartitionQueryFilter filter);

    Map<String, Long> countByTableGuid(@NotNull Multimap<T, String> tableGuidByDsBO);

    Map<String, Long> countByTableName(@NotNull T dataSourceBO, @NotNull List<String> tableNames);

    List<PartitionBO> getMinMax(@NotNull T dataSourceBO, @NotNull String tableName);
}

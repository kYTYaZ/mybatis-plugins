package com.fishqq.meta.services;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.alibaba.dt.dataphin.meta.client.common.ColumnQueryFilter;
import com.alibaba.dt.dataphin.meta.core.bo.ColumnBO;
import com.alibaba.dt.dataphin.meta.core.bo.datasource.DataSourceBO;

import com.google.common.collect.Multimap;

/**
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/9/27
 */
public interface MetastoreColumnService<T extends DataSourceBO> {
    Map<String, List<ColumnBO>> listByTableGuid(@NotNull Multimap<T, String> tableGuidByDsBO,
                                                @NotNull ColumnQueryFilter filter);

    Map<String, List<ColumnBO>> listByTableName(@NotNull T dataSourceBO,
                                                @NotNull List<String> tableNames,
                                                @NotNull ColumnQueryFilter filter);

    Map<String, Long> countByTableGuid(@NotNull Multimap<T, String> tableGuidByDsBO);

    Map<String, Long> countByTableName(@NotNull T dataSourceBO,
                                       @NotNull List<String> tableNames);
}

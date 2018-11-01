package com.alibaba.dt.dataphin.meta.core.service;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.alibaba.dt.dataphin.meta.client.common.ColumnQueryFilter;
import com.alibaba.dt.dataphin.meta.core.bo.ColumnBO;

/**
 * 字段service
 *
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/9/13
 */
public interface ColumnService {
    Map<String, List<ColumnBO>> listByTableGuid(@NotNull List<String> tableGuids, @NotNull ColumnQueryFilter filter);

    Map<String, List<ColumnBO>> listByTableName(@NotNull Long dataSourceId,
                                                @NotNull List<String> tableNames,
                                                @NotNull ColumnQueryFilter filter);

    Map<String, Long> countByTableGuid(@NotNull Long tenantId,
                                       @NotNull List<String> tableGuids,
                                       @NotNull Boolean isRealTime);

    Map<String, Long> countByTableName(@NotNull Long tenantId,
                                       @NotNull Long dataSourceId,
                                       @NotNull List<String> tableNames,
                                       @NotNull Boolean isRealTime);
}

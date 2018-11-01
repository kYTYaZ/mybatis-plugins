package com.alibaba.dt.dataphin.meta.core.service;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import com.alibaba.dt.dataphin.meta.client.common.PaginatedResult;
import com.alibaba.dt.dataphin.meta.client.common.TableQueryFilter;
import com.alibaba.dt.dataphin.meta.core.bo.TableBO;
import com.alibaba.dt.dataphin.meta.core.common.util.CollectionUtil;

/**
 * 表service
 *
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/9/13
 */
public interface TableService {
    /**
     * 由表guid查询物理表
     *
     * @param guids
     * @param filter
     * @return
     */
    List<TableBO> listByGuid(@Nonnull List<String> guids, @Nonnull TableQueryFilter filter);

    default TableBO getByGuid(@NotNull String guid, @NotNull TableQueryFilter filter) {
        return CollectionUtil.first(this.listByGuid(Arrays.asList(guid), filter));
    }

    /**
     * 由表name查询物理表
     *
     * @param dataSourceIds
     * @param tableNames
     * @param filter
     * @return
     */
    List<TableBO> listByName(@Nonnull List<Long> dataSourceIds,
                             @Nonnull List<String> tableNames,
                             @Nonnull TableQueryFilter filter);

    default TableBO getByName(@Nonnull Long dataSourceId,
                              @Nonnull String tableName,
                              @Nonnull TableQueryFilter filter) {
        return CollectionUtil.first(this.listByName(Arrays.asList(dataSourceId), Arrays.asList(tableName), filter));
    }

    /**
     * 搜索物理表
     *
     * @param dataSourceIds
     * @param keyword
     * @param filter
     * @return
     */
    PaginatedResult<TableBO> search(@Nonnull List<Long> dataSourceIds,
                                    @Nullable String keyword,
                                    @Nonnull TableQueryFilter filter);

    /**
     * 统计搜索到的表的个数
     *
     * @param dataSourceIds
     * @param keyword
     * @param filter
     * @return
     */
    Long countSearchedTables(@Nonnull List<Long> dataSourceIds,
                             @Nullable String keyword,
                             @Nonnull TableQueryFilter filter);

    /**
     * 在指定的数据源所在集群下搜索物理表
     *
     * @param tenantId
     * @param dataSourceId
     * @param keyword
     * @param filter
     * @return
     */
    PaginatedResult<TableBO> searchInCluster(@Nonnull Long tenantId,
                                             @Nonnull Long dataSourceId,
                                             @Nonnull String keyword,
                                             @Nonnull TableQueryFilter filter);

    /**
     * 获取在搜索结果中的所有datasource
     *
     * @param dataSourceIds
     * @param keyword
     * @param filter
     * @return
     */
    List<Long> listDataSourceInSearchedResult(@Nonnull List<Long> dataSourceIds,
                                              @Nullable String keyword,
                                              @NotNull TableQueryFilter filter);

    /**
     * 搜索物理表名
     *
     * @param dataSourceIds
     * @param keyword
     * @param filter
     * @return
     */
    PaginatedResult<String> searchName(@Nonnull List<Long> dataSourceIds,
                                       @Nullable String keyword,
                                       @Nonnull TableQueryFilter filter);
}

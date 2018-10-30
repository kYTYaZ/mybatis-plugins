package com.fishqq.meta.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import com.alibaba.dt.dataphin.common.lang.criteria.PaginationCriteria;
import com.alibaba.dt.dataphin.meta.client.common.PaginatedResult;
import com.alibaba.dt.dataphin.meta.client.common.TableQueryFilter;
import com.alibaba.dt.dataphin.meta.core.bo.TableBO;
import com.alibaba.dt.dataphin.meta.core.bo.datasource.DataSourceBO;
import com.alibaba.dt.onedata.metaguid.GuidUtil;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

/**
 * 物理数据源表元数据接口
 *
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/1/25
 */
public interface MetastoreTableService<T extends DataSourceBO> {
    /**
     * 由表guid查找物理表
     *
     * @param tableGuidByDsBO
     * @param filter
     * @return
     */
    List<TableBO> listByGuid(@NotNull Multimap<T, String> tableGuidByDsBO,
                             @NotNull TableQueryFilter filter);

    /**
     * 由表name查找物理表
     *
     * @param dataSourceBOs
     * @param tableNames
     * @param filter
     * @return
     */
    default List<TableBO> listByName(@NotNull List<T> dataSourceBOs,
                                     @NotNull List<String> tableNames,
                                     @NotNull TableQueryFilter filter) {
        Multimap<T, String> tableGuidByDsBO = ArrayListMultimap.create();

        dataSourceBOs.stream().forEach(dsBO -> {
            List<String> tableGuids = tableNames.stream()
                .map(tableName -> GuidUtil.createTableGuid(dsBO.getGuid(), tableName))
                .collect(Collectors.toList());

            tableGuidByDsBO.putAll(dsBO, tableGuids);
        });

        return listByGuid(tableGuidByDsBO, filter);
    }

    /**
     * 搜索物理表
     *
     * @param dataSourceBO
     * @param keyword
     * @param filter
     * @return
     */
    PaginatedResult<TableBO> search(@NotNull T dataSourceBO,
                                    @Nullable String keyword,
                                    @NotNull TableQueryFilter filter);

    /**
     * 多个数据源搜索物理表，不提供分页，只提供top n功能
     *
     * @param dataSourceBOs
     * @param keyword
     * @param filter
     * @return
     */
    default PaginatedResult<TableBO> search(@NotNull List<T> dataSourceBOs,
                                            @Nullable String keyword,
                                            @NotNull TableQueryFilter filter) {
        List<TableBO> result = Lists.newArrayList();
        int pageSize = filter.getPaginationCriteria().getPageSize();

        for (T ds : dataSourceBOs) {
            PaginatedResult<TableBO> bos = this.search(ds, keyword, filter);
            result.addAll(bos.getData());

            if (result.size() >= filter.getPaginationCriteria().getPageSize()) {
                break;
            } else {
                filter.setPaginationCriteria(PaginationCriteria.builder()
                    .doNotCount()
                    .page(0)
                    .pageSize(pageSize - result.size()).build());
            }
        }

        return new PaginatedResult<>(result);
    }

    /**
     * 统计搜索到物理表个数
     *
     * @param dataSourceBOs
     * @param keyword
     * @param filter
     * @return
     */
    Long countSearchedTables(@NotNull List<T> dataSourceBOs,
                             @Nullable String keyword,
                             @NotNull TableQueryFilter filter);

    /**
     * 搜索表name
     *
     * @param dataSourceBO
     * @param keyword
     * @param filter
     * @return
     */
    PaginatedResult<String> searchName(@NotNull T dataSourceBO,
                                       @Nullable String keyword,
                                       @NotNull TableQueryFilter filter);

    /**
     * 多个数据源下搜索表
     *
     * @param dataSourceBOs
     * @param keyword
     * @param filter
     * @return
     */
    default PaginatedResult<String> searchName(@NotNull List<T> dataSourceBOs,
                                               @Nullable String keyword,
                                               @NotNull TableQueryFilter filter) {
        List<String> result = Lists.newArrayList();
        int pageSize = filter.getPaginationCriteria().getPageSize();

        for (T ds : dataSourceBOs) {
            PaginatedResult<String> bos = this.searchName(ds, keyword, filter);
            result.addAll(bos.getData());

            if (result.size() >= filter.getPaginationCriteria().getPageSize()) {
                break;
            } else {
                filter.setPaginationCriteria(PaginationCriteria.builder()
                    .doNotCount()
                    .page(0)
                    .pageSize(pageSize - result.size()).build());
            }
        }

        return new PaginatedResult<>(result);
    }
}


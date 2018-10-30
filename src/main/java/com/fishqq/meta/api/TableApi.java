package com.fishqq.meta.api;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import com.alibaba.dt.dataphin.common.lang.result.ResultDTO;
import com.alibaba.dt.dataphin.meta.client.common.PaginatedResult;
import com.alibaba.dt.dataphin.meta.client.common.TableQueryFilter;
import com.alibaba.dt.dataphin.meta.client.model.table.Table;
import com.alibaba.dt.dataphin.meta.client.util.ResultUtil;

import com.fishqq.meta.api.common.TableQueryFilter;
import com.fishqq.meta.api.model.table.Table;

/**
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/9/13
 */
public interface TableApi {
    /**
     * 由表guid查询物理表
     *
     * @param guids
     * @param filter
     * @return
     */
    ResultDTO<List<Table>> listByGuid(List<String> guids,
                                      TableQueryFilter filter);

    /**
     * 由guid查询物理表
     *
     * @param guid
     * @param filter
     * @return
     */
    default ResultDTO<Table> getByGuid( String guid,
                                        TableQueryFilter filter) {
        return ResultUtil.getFirst(this.listByGuid(Arrays.asList(guid), filter));
    }

    /**
     * 由表name查询物理表
     *
     * @param dataSourceIds
     * @param tableNames
     * @param filter
     * @return
     */
    ResultDTO<List<Table>> listByName( List<Long> dataSourceIds,
                                       List<String> tableNames,
                                       TableQueryFilter filter);

    /**
     * 由name查询物理表
     *
     * @param dataSourceId
     * @param tableNames
     * @param filter
     * @return
     */
    default ResultDTO<List<Table>> listByName( Long dataSourceId,
                                               List<String> tableNames,
                                               TableQueryFilter filter) {
        return this.listByName(Arrays.asList(dataSourceId), tableNames, filter);
    }

    /**
     * 由name查询物理表
     *
     * @param dataSourceIds
     * @param tableName
     * @param filter
     * @return
     */
    default ResultDTO<List<Table>> listByName( List<Long> dataSourceIds,
                                               String tableName,
                                               TableQueryFilter filter) {
        return this.listByName(dataSourceIds, Arrays.asList(tableName), filter);
    }

    /**
     * 由name查询物理表
     *
     * @param dataSourceId
     * @param tableName
     * @param filter
     * @return
     */
    default ResultDTO<Table> getByName( Long dataSourceId,
                                        String tableName,
                                        TableQueryFilter filter) {
        return ResultUtil.getFirst(this.listByName(Arrays.asList(dataSourceId), Arrays.asList(tableName), filter));
    }

    /**
     * 搜索物理表
     *
     * @param dataSourceIds
     * @param keyword
     * @param filter
     * @return
     */
    ResultDTO<PaginatedResult<Table>> search( List<Long> dataSourceIds,
                                             @Nullable String keyword,
                                              TableQueryFilter filter);

    /**
     * 搜索物理表
     *
     * @param dataSourceId
     * @param keyword
     * @param filter
     * @return
     */
    default ResultDTO<PaginatedResult<Table>> search( Long dataSourceId,
                                                      String keyword,
                                                      TableQueryFilter filter) {
        return this.search(Arrays.asList(dataSourceId), keyword, filter);
    }

    /**
     * 列出数据源下所有物理表
     *
     * @param dataSourceIds
     * @param filter
     * @return
     */
    default ResultDTO<PaginatedResult<Table>> list( List<Long> dataSourceIds,
                                                    TableQueryFilter filter) {
        return this.search(dataSourceIds, null, filter);
    }

    /**
     * 列出所有的物理表
     *
     * @param dataSourceId
     * @param filter
     * @return
     */
    default ResultDTO<PaginatedResult<Table>> list( Long dataSourceId,
                                                    TableQueryFilter filter) {
        return this.list(Arrays.asList(dataSourceId), filter);
    }

    /**
     * 统计搜索到的表的个数
     *
     * @param dataSourceIds
     * @param keyword
     * @param filter
     * @return
     */
    ResultDTO<Long> countSearchedTables( List<Long> dataSourceIds,
                                        @Nullable String keyword,
                                         TableQueryFilter filter);

    /**
     * 统计数据源下的表个数
     *
     * @param dataSourceIds
     * @param filter
     * @return
     */
    default ResultDTO<Long> countTables( List<Long> dataSourceIds,
                                         TableQueryFilter filter) {
        return this.countSearchedTables(dataSourceIds, null, filter);
    }

    /**
     * 统计数据源下的表个数
     *
     * @param dataSourceId
     * @param filter
     * @return
     */
    default ResultDTO<Long> countTables( Long dataSourceId,
                                         TableQueryFilter filter) {
        return this.countSearchedTables(Arrays.asList(dataSourceId), null, filter);
    }

    /**
     * 在指定的数据源所在集群下搜索物理表
     *
     * @param tenantId
     * @param dataSourceId
     * @param keyword
     * @param filter
     * @return
     */
    ResultDTO<PaginatedResult<Table>> searchInCluster( Long tenantId,
                                                       Long dataSourceId,
                                                       String keyword,
                                                       TableQueryFilter filter);

    /**
     * 获取在搜索结果中的所有datasource
     *
     * @param dataSourceIds
     * @param keyword
     * @param filter
     * @return
     */
    ResultDTO<List<Long>> listDataSourceInSearchedTables( List<Long> dataSourceIds,
                                                         @Nullable String keyword,
                                                         TableQueryFilter filter);

    /**
     * 搜索物理表名
     *
     * @param dataSourceIds
     * @param keyword
     * @param filter
     * @return
     */
    ResultDTO<PaginatedResult<String>> searchName( List<Long> dataSourceIds,
                                                  @Nullable String keyword,
                                                   TableQueryFilter filter);

    /**
     * 搜索物理表名
     *
     * @param dataSourceId
     * @param keyword
     * @param filter
     * @return
     */
    default ResultDTO<PaginatedResult<String>> searchName( Long dataSourceId,
                                                           String keyword,
                                                           TableQueryFilter filter) {
        return this.searchName(Arrays.asList(dataSourceId), keyword, filter);
    }

    /**
     * 列出数据源下所有物理表名
     *
     * @param dataSourceIds
     * @param filter
     * @return
     */
    default ResultDTO<PaginatedResult<String>> listName( List<Long> dataSourceIds,
                                                         TableQueryFilter filter) {
        return this.searchName(dataSourceIds, null, filter);
    }

    /**
     * 列出数据源下所有物理表名
     *
     * @param filter
     * @return
     */
    default ResultDTO<PaginatedResult<String>> listName( Long dataSourceId,
                                                         TableQueryFilter filter) {
        return this.listName(Arrays.asList(dataSourceId), filter);
    }
}

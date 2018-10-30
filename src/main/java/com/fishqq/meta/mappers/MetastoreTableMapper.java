package com.fishqq.meta.mappers;

import java.util.List;

import com.alibaba.dt.dataphin.common.lang.criteria.PaginationCriteria;
import com.alibaba.dt.dataphin.common.mybatis.interceptor.pagination.PaginationResult;
import com.alibaba.dt.dataphin.meta.client.model.column.Range;
import com.alibaba.dt.dataphin.meta.core.dal.dataobject.ColumnDO;
import com.alibaba.dt.dataphin.meta.core.dal.dataobject.PartitionDO;
import com.alibaba.dt.dataphin.meta.core.dal.dataobject.TableDO;

import org.apache.ibatis.annotations.Param;

/**
 * table meta info mapper
 *
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/9/4
 */
public interface MetastoreTableMapper {
    /**
     * 获取表信息
     *
     * @param database
     * @param schema
     * @param tableNames
     * @return
     */
    List<TableDO> listTable(@Param("database") String database,
                            @Param("schema") String schema,
                            @Param("tableNames") List<String> tableNames);

    /**
     * 搜索表
     *
     * @param database
     * @param schema
     * @param keyword
     * @param byPrefix
     * @param paginationCriteria
     * @return
     */
    PaginationResult searchTable(@Param("database") String database,
                                 @Param("schema") String schema,
                                 @Param("keyword") String keyword,
                                 @Param("byPrefix") boolean byPrefix,
                                 @Param("filterBbox") boolean filterBbox,
                                 @Param("page") PaginationCriteria paginationCriteria);

    /**
     * 搜索表名
     *
     * @param database
     * @param schema
     * @param keyword
     * @param byPrefix
     * @param paginationCriteria
     * @return
     */
    PaginationResult searchTableName(@Param("database") String database,
                                     @Param("schema") String schema,
                                     @Param("keyword") String keyword,
                                     @Param("byPrefix") boolean byPrefix,
                                     @Param("filterBbox") boolean filterBbox,
                                     @Param("page") PaginationCriteria paginationCriteria);

    /**
     * 表个数
     *
     * @param database
     * @param schema
     * @param keyword
     * @param byPrefix
     * @return
     */
    Long countTable(@Param("database") String database,
                    @Param("schema") String schema,
                    @Param("keyword") String keyword,
                    @Param("byPrefix") boolean byPrefix,
                    @Param("filterBbox") boolean filterBbox);

    /**
     * 字段个数
     *
     * @param database
     * @param schema
     * @param tableName
     * @return
     */
    Long countColumn(@Param("database") String database,
                     @Param("schema") String schema,
                     @Param("tableName") String tableName);

    /**
     * 获取表所有字段
     *
     * @param database
     * @param schema
     * @param tableName
     * @return
     */
    List<ColumnDO> listColumns(@Param("database") String database,
                               @Param("schema") String schema,
                               @Param("tableName") String tableName);

    /**
     * 获取分区by range
     *
     * @param database
     * @param schema
     * @param tableName
     * @param ranges
     * @return
     */
    List<PartitionDO> listPartitionByRange(@Param("database") String database,
                                           @Param("schema") String schema,
                                           @Param("tableName") String tableName,
                                           @Param("ranges") List<Range<String>> ranges);

    /**
     * 获取分区by page
     *
     * @param database
     * @param schema
     * @param tableName
     * @param paginationCriteria
     * @return
     */
    List<PartitionDO> listPartitionByPage(@Param("database") String database,
                                          @Param("schema") String schema,
                                          @Param("tableName") String tableName,
                                          @Param("page") PaginationCriteria paginationCriteria);

    /**
     * 获取最小最大分区
     *
     * @param database
     * @param schema
     * @param tableName
     * @return
     */
    List<PartitionDO> getMinMaxPartition(@Param("database") String database,
                                         @Param("schema") String schema,
                                         @Param("tableName") String tableName);

    /**
     * 分区个数
     *
     * @param database
     * @param schema
     * @param tableName
     * @return
     */
    Long countPartition(@Param("database") String database,
                        @Param("schema") String schema,
                        @Param("tableName") String tableName);
}
package com.fishqq.meta.repositories;

import java.util.List;

import com.alibaba.dt.dataphin.common.lang.criteria.PaginationCriteria;
import com.alibaba.dt.dataphin.common.mybatis.interceptor.pagination.PaginationResult;
import com.alibaba.dt.dataphin.meta.client.model.column.Range;
import com.alibaba.dt.dataphin.meta.core.bo.datasource.JdbcDataSourceBO;
import com.alibaba.dt.dataphin.meta.core.dal.dataobject.ColumnDO;
import com.alibaba.dt.dataphin.meta.core.dal.dataobject.PartitionDO;
import com.alibaba.dt.dataphin.meta.core.dal.dataobject.TableDO;

/**
 * table repository
 *
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/9/5
 */
public interface MetastoreTableRepository {
    /**
     * 获取表详细信息
     *
     * @param dataSourceBO
     * @param tableNames
     * @return
     */
    List<TableDO> listTable(JdbcDataSourceBO dataSourceBO, List<String> tableNames);

    /**
     * 搜索表
     *
     * @param dataSourceBO
     * @param keyword
     * @param criteria
     * @return
     */
    PaginationResult searchTable(JdbcDataSourceBO dataSourceBO,
                                 String keyword,
                                 boolean byPrefix,
                                 boolean needBbox,
                                 PaginationCriteria criteria);

    /**
     * 搜索表名
     *
     * @param dataSourceBO
     * @param keyword
     * @param criteria
     * @return
     */
    PaginationResult searchTableName(JdbcDataSourceBO dataSourceBO,
                                     String keyword,
                                     boolean byPrefix,
                                     boolean needBbox,
                                     PaginationCriteria criteria);

    /**
     * 获取表数目
     *
     * @param dataSourceBOs
     * @param keyword
     * @param byPrefix
     * @return
     */
    Long countTable(List<JdbcDataSourceBO> dataSourceBOs, String keyword, boolean byPrefix, boolean needBbox);

    /**
     * 获取表详细信息
     *
     * @param dataSourceBO
     * @param tableName
     * @return
     */
    List<ColumnDO> listColumn(JdbcDataSourceBO dataSourceBO, String tableName);

    /**
     * 字段个数
     *
     * @param dataSourceBO
     * @param tableName
     * @return
     */
    Long countColumn(JdbcDataSourceBO dataSourceBO, String tableName);

    /**
     * 获取分区区间
     *
     * @param dataSourceBO
     * @param tableName
     * @param ranges
     * @return
     */
    List<PartitionDO> listPartitionByRange(JdbcDataSourceBO dataSourceBO,
                                           String tableName,
                                           List<Range<String>> ranges);

    /**
     * 获取分区分页
     *
     * @param dataSourceBO
     * @param tableName
     * @param page
     * @return
     */
    List<PartitionDO> listPartitionByPage(JdbcDataSourceBO dataSourceBO,
                                          String tableName,
                                          PaginationCriteria page);

    /**
     * 获取最小最大分区
     *
     * @param dataSourceBO
     * @param tableName
     * @return
     */
    List<PartitionDO> getMinMaxPartition(JdbcDataSourceBO dataSourceBO, String tableName);

    /**
     * 分区个数
     *
     * @param dataSourceBO
     * @param tableName
     * @return
     */
    Long countPartition(JdbcDataSourceBO dataSourceBO, String tableName);
}

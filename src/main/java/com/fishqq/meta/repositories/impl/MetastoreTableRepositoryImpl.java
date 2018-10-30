package com.fishqq.meta.repositories.impl;

import java.util.List;
import java.util.function.Function;

import javax.annotation.Resource;

import com.alibaba.dt.dataphin.common.lang.criteria.PaginationCriteria;
import com.alibaba.dt.dataphin.common.mybatis.interceptor.pagination.PaginationResult;
import com.alibaba.dt.dataphin.meta.client.model.column.Range;
import com.alibaba.dt.dataphin.meta.core.bo.datasource.EmrHiveDataSourceBO;
import com.alibaba.dt.dataphin.meta.core.bo.datasource.HadoopDataSourceBO;
import com.alibaba.dt.dataphin.meta.core.bo.datasource.HiveDataSourceBO;
import com.alibaba.dt.dataphin.meta.core.bo.datasource.JdbcDataSourceBO;
import com.alibaba.dt.dataphin.meta.core.common.mybatis.MapperFactory;
import com.alibaba.dt.dataphin.meta.core.common.util.RegularUtil;
import com.alibaba.dt.dataphin.meta.core.dal.dataobject.ColumnDO;
import com.alibaba.dt.dataphin.meta.core.dal.dataobject.PartitionDO;
import com.alibaba.dt.dataphin.meta.core.dal.dataobject.TableDO;
import com.alibaba.dt.dataphin.meta.core.dal.mapper.metastore.DataSourceFactory;
import com.alibaba.dt.dataphin.meta.core.dal.mapper.metastore.MetastoreTableMapper;
import com.alibaba.dt.dataphin.meta.core.dal.repository.MetastoreTableRepository;
import com.alibaba.dt.onedata.metaguid.GuidUtil;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * table repository impl
 *
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/9/5
 */
@Service
public class MetastoreTableRepositoryImpl implements MetastoreTableRepository {
    @Resource
    @Qualifier("TableMapperFactory")
    private MapperFactory<MetastoreTableMapper> tableMapperFactory;

    @Override
    public List<TableDO> listTable(JdbcDataSourceBO dsBO, List<String> tableNames) {
        return this.callMapper(dsBO, mapper -> fillTables(
            dsBO, mapper.listTable(getDatabase(dsBO), dsBO.getSchema(), tableNames)));
    }

    @Override
    public PaginationResult searchTable(JdbcDataSourceBO dsBO,
                                        String keyword,
                                        boolean byPrefix,
                                        boolean needBbox,
                                        PaginationCriteria criteria) {
        Function<MetastoreTableMapper, PaginationResult> method = (mapper) -> {
            String catalog = getDatabase(dsBO);
            return fillTables(dsBO, mapper.searchTable(
                catalog, dsBO.getSchema(), keyword, byPrefix, !needBbox, criteria));
        };

        return this.callMapper(dsBO, method);
    }

    private static PaginationResult fillTables(JdbcDataSourceBO dsBO, PaginationResult tableDOS) {
        tableDOS.stream().forEach(item -> fillTable(dsBO, (TableDO)item));
        return tableDOS;
    }

    private static List<TableDO> fillTables(JdbcDataSourceBO dsBO, List<TableDO> tableDOS) {
        tableDOS.stream().forEach(tableDO -> fillTable(dsBO, tableDO));
        return tableDOS;
    }

    private static TableDO fillTable(JdbcDataSourceBO dsBO, TableDO tableDO) {
        tableDO.setDataSourceType(dsBO.getDataSourceEnum().getTypeName());
        tableDO.setDataSourceGuid(dsBO.getGuid());
        tableDO.setGuid(GuidUtil.createTableGuid(dsBO.getGuid(), tableDO.getName()));
        tableDO.setDisplayName(tableDO.getComment());
        tableDO.setBbox(RegularUtil.isBboxTable(tableDO.getName()));

        return tableDO;
    }

    @Override
    public PaginationResult searchTableName(JdbcDataSourceBO dsBO,
                                            String keyword,
                                            boolean byPrefix,
                                            boolean needBbox,
                                            PaginationCriteria criteria) {
        Function<MetastoreTableMapper, PaginationResult> method = (mapper) -> {
            String catalog = getDatabase(dsBO);
            return mapper.searchTableName(catalog, dsBO.getSchema(), keyword, byPrefix, !needBbox, criteria);
        };

        return this.callMapper(dsBO, method);
    }

    @Override
    public Long countTable(List<JdbcDataSourceBO> dataSourceBOs, String keyword, boolean byPrefix, boolean needBbox) {
        return dataSourceBOs.stream().mapToLong(dsBO ->
            this.callMapper(dsBO, mapper -> mapper.countTable(
                getDatabase(dsBO), dsBO.getSchema(), keyword, byPrefix, !needBbox)))
            .count();
    }

    @Override
    public List<ColumnDO> listColumn(JdbcDataSourceBO dsBO, String tableName) {
        return this.callMapper(dsBO, mapper -> {
            List<ColumnDO> columnDOS = mapper.listColumns(getDatabase(dsBO), dsBO.getSchema(), tableName);

            // 元仓中没有displayname，使用注释代替
            columnDOS.stream().forEach(columnDO -> columnDO.setDisplayName(columnDO.getComment()));

            // 如果分区字段和普通字段是分开计算seqnumber的，需要合并
            if (columnDOS.size() > 0) {
                long partitionColumns = columnDOS.stream().filter(ColumnDO::isPt).count();

                if (partitionColumns > 0 && partitionColumns != columnDOS.size()) {
                    int maxNormalSeqNumber = 0;
                    int minPartitionSeqNumber = 0;
                    for (ColumnDO columnDO : columnDOS) {
                        if (columnDO.isPt()) {
                            if (minPartitionSeqNumber > columnDO.getSeqNumber()) {
                                minPartitionSeqNumber = columnDO.getSeqNumber();
                            }
                        } else {
                            if (maxNormalSeqNumber < columnDO.getSeqNumber()) {
                                maxNormalSeqNumber = columnDO.getSeqNumber();
                            }
                        }
                    }

                    int start = maxNormalSeqNumber + (minPartitionSeqNumber == 0 ? 1 : minPartitionSeqNumber);

                    columnDOS.stream().forEach(columnDO -> {
                        if (columnDO.isPt()) {
                            columnDO.setSeqNumber(columnDO.getSeqNumber() + start);
                        }
                    });
                }
            }

            return fillColumns(dsBO, tableName, columnDOS);
        });
    }

    private static List<ColumnDO> fillColumns(JdbcDataSourceBO dsBO, String tableName, List<ColumnDO> columnDOs) {
        columnDOs.stream().forEach(columnDO -> fillColumn(dsBO, tableName, columnDO));
        return columnDOs;
    }

    private static ColumnDO fillColumn(JdbcDataSourceBO dsBO, String tableName, ColumnDO columnDO) {
        columnDO.setDataSourceType(dsBO.getDataSourceEnum().getTypeName());
        columnDO.setDataSourceGuid(dsBO.getGuid());
        columnDO.setTableName(tableName);
        columnDO.setTableGuid(GuidUtil.createTableGuid(dsBO.getGuid(), tableName));
        columnDO.setGuid(GuidUtil.createColumnGuid(columnDO.getTableGuid(), columnDO.getName()));

        return columnDO;
    }

    @Override
    public Long countColumn(JdbcDataSourceBO dsBO, String tableName) {
        return this.callMapper(dsBO, mapper -> mapper.countColumn(getDatabase(dsBO), dsBO.getSchema(), tableName));
    }

    @Override
    public List<PartitionDO> listPartitionByRange(JdbcDataSourceBO dsBO,
                                                  String tableName,
                                                  List<Range<String>> ranges) {
        if (dsBO.hasPartition()) {
            return this.callMapper(dsBO, mapper -> fillPartitionDOs(dsBO, tableName,
                mapper.listPartitionByRange(getDatabase(dsBO), dsBO.getSchema(), tableName, ranges)));
        } else {
            return Lists.newArrayList();
        }
    }

    @Override
    public List<PartitionDO> listPartitionByPage(JdbcDataSourceBO dsBO,
                                                 String tableName,
                                                 PaginationCriteria page) {
        if (dsBO.hasPartition()) {
            return this.callMapper(dsBO, mapper -> fillPartitionDOs(dsBO, tableName,
                mapper.listPartitionByPage(getDatabase(dsBO), dsBO.getSchema(), tableName, page)));
        } else {
            return Lists.newArrayList();
        }
    }

    @Override
    public List<PartitionDO> getMinMaxPartition(JdbcDataSourceBO dsBO, String tableName) {
        if (dsBO.hasPartition()) {
            return this.callMapper(dsBO, mapper -> fillPartitionDOs(dsBO, tableName,
                mapper.getMinMaxPartition(getDatabase(dsBO), dsBO.getSchema(), tableName)));
        } else {
            return Lists.newArrayList();
        }
    }

    @Override
    public Long countPartition(JdbcDataSourceBO dsBO, String tableName) {
        if (dsBO.hasPartition()) {
            return this.callMapper(dsBO, mapper -> mapper.countPartition(
                getDatabase(dsBO), dsBO.getSchema(), tableName));
        } else {
            return 0L;
        }
    }

    private static List<PartitionDO> fillPartitionDOs(JdbcDataSourceBO dsBO,
                                                      String tableName,
                                                      List<PartitionDO> partitionDOs) {
        partitionDOs.stream().forEach(partitionDO -> fillPartitionDO(dsBO, tableName, partitionDO));
        return partitionDOs;
    }

    private static PartitionDO fillPartitionDO(JdbcDataSourceBO dsBO, String tableName, PartitionDO partitionDO) {
        partitionDO.setDataSourceType(dsBO.getDataSourceEnum().getTypeName());
        partitionDO.setDataSourceGuid(dsBO.getGuid());
        partitionDO.setTableGuid(GuidUtil.createTableGuid(dsBO.getGuid(), tableName));
        partitionDO.setGuid(GuidUtil.createPartitionGuid(partitionDO.getTableGuid(), partitionDO.getName()));

        return partitionDO;
    }

    private <T> T callMapper(JdbcDataSourceBO dsBO, Function<MetastoreTableMapper, T> handler) {
        JdbcDataSourceBO meta = getMeta(dsBO);

        return this.tableMapperFactory.callMapper(
            dsBO.getDataSourceEnum(),
            meta.getDataSourceEnum(),
            DataSourceFactory.get(meta),
            mapper -> handler.apply(mapper)
        );
    }

    private JdbcDataSourceBO getMeta(JdbcDataSourceBO dataSourceBO) {
        switch (dataSourceBO.getDataSourceEnum()) {
            case HIVE:
                return ((HiveDataSourceBO)dataSourceBO).getHiveMetaDs();
            case EMR_HIVE:
                return ((EmrHiveDataSourceBO)dataSourceBO).getHiveMetaDs();
            case HADOOP:
                return ((HadoopDataSourceBO)dataSourceBO).getHiveMetaDs();
            default:
                return dataSourceBO;
        }
    }

    private String getDatabase(JdbcDataSourceBO dsBO) {
        return dsBO.isDatabaseSameWithSchema() ? null : dsBO.getDbName();
    }
}

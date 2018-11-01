package com.alibaba.dt.dataphin.meta.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.dt.dataphin.meta.client.common.PaginatedResult;
import com.alibaba.dt.dataphin.meta.client.common.TableQueryFilter;
import com.alibaba.dt.dataphin.meta.core.bo.DataSourceMappingBO;
import com.alibaba.dt.dataphin.meta.core.bo.TableBO;
import com.alibaba.dt.dataphin.meta.core.bo.datasource.JdbcDataSourceBO;
import com.alibaba.dt.dataphin.meta.core.service.TableService;
import com.alibaba.dt.dataphin.meta.core.service.datasource.DataSourceMappingService;
import com.alibaba.dt.dataphin.meta.core.service.metastore.MetastoreTableService;
import com.alibaba.dt.dataphin.meta.core.service.synced.SyncedTableService;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * 白路 bailu.zjj@alibaba-inc.com
 * 2018/9/13
 */
@Service
public class TableServiceImpl implements TableService {
    @Resource
    private DataSourceMappingService mappingService;

    @Resource
    private SyncedTableService syncedTableService;

    @Resource
    @Qualifier("jdbc")
    private MetastoreTableService jdbcTableService;

    @Resource
    @Qualifier("maxCompute")
    private MetastoreTableService maxComputeTableService;

    @Override
    public List<TableBO> listByGuid(List<String> tableGuids, TableQueryFilter filter) {
        DataSourceMappingBO mappingBO = this.mappingService.getByTableGuid(
            filter.getTenantId(), tableGuids, filter.isNeedRealTime());

        return this.listByMapping(mappingBO, filter);
    }

    @Override
    public List<TableBO> listByName(List<Long> dataSourceIds,
                                    List<String> tableNames,
                                    TableQueryFilter filter) {
        DataSourceMappingBO dataSourceDataBO = this.mappingService.getByTableName(
            filter.getTenantId(), dataSourceIds, tableNames, filter.isNeedRealTime());

        return this.listByMapping(dataSourceDataBO, filter);
    }

    private List<TableBO> listByMapping(DataSourceMappingBO mappingBO, TableQueryFilter filter) {
        List<TableBO> result = Lists.newArrayList();

        if (filter.isNeedRealTime()) {
            MappingHandler.handleTableByType(mappingBO,
                jdbcMap -> result.addAll(this.jdbcTableService.listByGuid(jdbcMap, filter)),
                maxComputeMap -> result.addAll(this.maxComputeTableService.listByGuid(maxComputeMap, filter)));
        } else {
            if (mappingBO.hasModel()) {
                result.addAll(this.syncedTableService.listByGuid(mappingBO.modelTableGuids(), filter));
            }

            if (mappingBO.hasPhysical()) {
                result.addAll(this.jdbcTableService.listByGuid(mappingBO.physicalDataSourceBO2TableGuidMap(), filter));
            }
        }

        return result;
    }

    @Override
    public PaginatedResult<TableBO> search(List<Long> dataSourceIds,
                                           String keyword,
                                           TableQueryFilter filter) {
        DataSourceMappingBO mappingBO = this.mappingService.getByDataSourceId(
            filter.getTenantId(), dataSourceIds, filter.isNeedRealTime());

        return searchByMapping(mappingBO, keyword, filter);
    }

    @Override
    public PaginatedResult<TableBO> searchInCluster(Long tenantId,
                                                    Long dataSourceId,
                                                    String keyword,
                                                    TableQueryFilter filter) {
        DataSourceMappingBO mappingBO = this.mappingService.getInCluster(tenantId, dataSourceId);

        return this.searchByMapping(mappingBO, keyword, filter);
    }

    private PaginatedResult<TableBO> searchByMapping(DataSourceMappingBO mappingBO, String keyword, TableQueryFilter filter) {
        if (filter.isNeedRealTime()) {
            List<TableBO> result = Lists.newArrayList();

            MappingHandler.handleByType(mappingBO,
                jdbcs -> result.addAll(this.jdbcTableService.search(jdbcs, keyword, filter).getData()),
                mcs -> result.addAll(this.maxComputeTableService.search(mcs, keyword, filter).getData())
            );

            return new PaginatedResult<>(result.size(), result);
        } else {
            if (mappingBO.hasModel()) {
                return this.syncedTableService.search(mappingBO.modelDataSourceGuids(), keyword, filter);
            }

            if (mappingBO.hasPhysical()) {
                List<JdbcDataSourceBO> dsBOs = mappingBO.physicalDataSourceBOs();
                return this.jdbcTableService.search(dsBOs, keyword, filter);
            }

            return new PaginatedResult<>(0, Lists.newArrayList());
        }
    }

    @Override
    public Long countSearchedTables(List<Long> dataSourceIds,
                                    String keyword,
                                    TableQueryFilter filter) {
        long total = 0;

        DataSourceMappingBO mappingBO = this.mappingService.getByDataSourceId(
            filter.getTenantId(), dataSourceIds, filter.isNeedRealTime());

        if (mappingBO.hasModel()) {
            total += this.syncedTableService.countSearchedTables(mappingBO.modelDataSourceGuids(), keyword, filter);
        }

        if (mappingBO.hasPhysical()) {
            total += this.jdbcTableService.countSearchedTables(mappingBO.physicalDataSourceBOs(), keyword, filter);
        }

        return total;
    }

    @Override
    public List<Long> listDataSourceInSearchedResult(List<Long> dataSourceIds,
                                                     String keyword,
                                                     TableQueryFilter filter) {
        List<Long> result = Lists.newArrayList();

        DataSourceMappingBO mappingBO = this.mappingService.getByDataSourceId(
            filter.getTenantId(), dataSourceIds, filter.isNeedRealTime());

        if (mappingBO.hasModel()) {
            List<String> modelDsGuids = mappingBO.modelDataSourceGuids();
            List<String> dsGuids = this.syncedTableService.listDataSourceInSearchResult(modelDsGuids, keyword, filter);
            dsGuids.stream().forEach(dsGuid -> result.addAll(mappingBO.dataSourceIdOf(dsGuid)));
        }

        if (mappingBO.hasPhysical()) {
            mappingBO.physicalDataSourceBOs().stream().forEach(dsBO -> {
                PaginatedResult paginatedResult = this.jdbcTableService.searchName(dsBO, keyword, filter);
                if (paginatedResult.getRecordCount() > 0 ||
                    (paginatedResult.getData() != null && paginatedResult.getData().size() > 0)) {
                    result.addAll(mappingBO.dataSourceIdOf(dsBO.getGuid()));
                }
            });
        }

        return result;
    }

    @Override
    public PaginatedResult<String> searchName(List<Long> dataSourceIds,
                                              String keyword,
                                              TableQueryFilter filter) {
        DataSourceMappingBO mappingBO = this.mappingService.getByDataSourceId(
            filter.getTenantId(), dataSourceIds, filter.isNeedRealTime());

        if (filter.isNeedRealTime()) {
            List<String> result = Lists.newArrayList();

            MappingHandler.handleByType(mappingBO,
                jdbcs -> result.addAll(this.jdbcTableService.searchName(jdbcs, keyword, filter).getData()),
                mcs -> result.addAll(this.maxComputeTableService.searchName(mcs, keyword, filter).getData()));

            return new PaginatedResult<>(result.size(), result);
        } else {
            if (mappingBO.hasModel()) {
                return this.syncedTableService.searchName(mappingBO.modelDataSourceGuids(), keyword, filter);
            }

            if (mappingBO.hasPhysical()) {
                return this.jdbcTableService.searchName(mappingBO.physicalDataSourceBOs(), keyword, filter);
            }

            return new PaginatedResult<>(0, Lists.newArrayList());
        }
    }
}

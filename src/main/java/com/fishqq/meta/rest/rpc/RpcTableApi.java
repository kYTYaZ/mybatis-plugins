package com.alibaba.dt.dataphin.meta.server.rpc;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.dt.dataphin.common.lang.result.ResultDTO;
import com.alibaba.dt.dataphin.meta.client.api.TableApi;
import com.alibaba.dt.dataphin.meta.client.common.PaginatedResult;
import com.alibaba.dt.dataphin.meta.client.common.TableQueryFilter;
import com.alibaba.dt.dataphin.meta.client.model.table.Table;
import com.alibaba.dt.dataphin.meta.core.bo.TableBO;
import com.alibaba.dt.dataphin.meta.core.converter.TableConverter;
import com.alibaba.dt.dataphin.meta.core.service.TableService;
import com.alibaba.dt.dataphin.meta.server.ApiLogger;
import com.alibaba.dt.onedata.rpc.annotation.MethodMapping;
import com.alibaba.dt.onedata.rpc.annotation.ServiceProvider;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/9/13
 */
@Service
@Qualifier("rpc")
@ServiceProvider
public class RpcTableApi extends ApiLogger implements TableApi {
    @Resource
    private TableService tableService;

    @Override
    @MethodMapping
    public ResultDTO<List<Table>> listByGuid(List<String> guids, TableQueryFilter filter) {
        return this.log(() -> {
            List<TableBO> bos = this.tableService.listByGuid(guids, filter);
            return TableConverter.toTables(bos, filter);
        }, "listByGuid", guids);
    }

    @Override
    @MethodMapping
    public ResultDTO<Table> getByGuid(String guid, TableQueryFilter filter) {
        return TableApi.super.getByGuid(guid, filter);
    }

    @Override
    @MethodMapping
    public ResultDTO<List<Table>> listByName(List<Long> dataSourceIds,
                                             List<String> tableNames,
                                             TableQueryFilter filter) {
        return this.log(() -> {
            List<TableBO> bos = this.tableService.listByName(dataSourceIds, tableNames, filter);
            return TableConverter.toTables(bos, filter);
        }, "listByName", dataSourceIds, tableNames);
    }

    @Override
    @MethodMapping
    public ResultDTO<List<Table>> listByName(Long dataSourceId, List<String> tableNames, TableQueryFilter filter) {
        return TableApi.super.listByName(dataSourceId, tableNames, filter);
    }

    @Override
    @MethodMapping
    public ResultDTO<List<Table>> listByName(List<Long> dataSourceIds, String tableName, TableQueryFilter filter) {
        return TableApi.super.listByName(dataSourceIds, tableName, filter);
    }

    @Override
    @MethodMapping
    public ResultDTO<Table> getByName(Long dataSourceId, String tableName, TableQueryFilter filter) {
        return TableApi.super.getByName(dataSourceId, tableName, filter);
    }

    @Override
    @MethodMapping
    public ResultDTO<PaginatedResult<Table>> search(List<Long> dataSourceIds, String keyword, TableQueryFilter filter) {
        return this.log(() -> {
            PaginatedResult<TableBO> bos = this.tableService.search(dataSourceIds, keyword, filter);
            return new PaginatedResult<>(bos.getRecordCount(), TableConverter.toTables(bos.getData(), filter));
        }, "search", dataSourceIds, keyword);
    }

    @Override
    @MethodMapping
    public ResultDTO<PaginatedResult<Table>> search(Long dataSourceId, String keyword, TableQueryFilter filter) {
        return TableApi.super.search(dataSourceId, keyword, filter);
    }

    @Override
    @MethodMapping
    public ResultDTO<PaginatedResult<Table>> list(List<Long> dataSourceIds, TableQueryFilter filter) {
        return TableApi.super.list(dataSourceIds, filter);
    }

    @Override
    @MethodMapping
    public ResultDTO<PaginatedResult<Table>> list(Long dataSourceId, TableQueryFilter filter) {
        return TableApi.super.list(dataSourceId, filter);
    }

    @Override
    @MethodMapping
    public ResultDTO<Long> countSearchedTables(List<Long> dataSourceIds, String keyword, TableQueryFilter filter) {
        return this.log(() -> this.tableService.countSearchedTables(dataSourceIds, keyword, filter),
            "countSearchedTables", dataSourceIds, keyword);
    }

    @Override
    @MethodMapping
    public ResultDTO<Long> countTables(List<Long> dataSourceIds, TableQueryFilter filter) {
        return TableApi.super.countTables(dataSourceIds, filter);
    }

    @Override
    @MethodMapping
    public ResultDTO<Long> countTables(Long dataSourceId, TableQueryFilter filter) {
        return TableApi.super.countTables(dataSourceId, filter);
    }

    @Override
    @MethodMapping
    public ResultDTO<PaginatedResult<Table>> searchInCluster(Long tenantId,
                                                             Long dataSourceId,
                                                             String keyword,
                                                             TableQueryFilter filter) {
        return this.log(() -> {
                PaginatedResult<TableBO> bos = this.tableService.searchInCluster(
                    tenantId, dataSourceId, keyword, filter);
                return new PaginatedResult<>(bos.getRecordCount(), TableConverter.toTables(bos.getData(), filter));
            },
            "searchInCluster", tenantId, dataSourceId, keyword);
    }

    @Override
    @MethodMapping
    public ResultDTO<List<Long>> listDataSourceInSearchedTables(List<Long> dataSourceIds,
                                                                String keyword,
                                                                TableQueryFilter filter) {
        return this.log(() -> this.tableService.listDataSourceInSearchedResult(dataSourceIds, keyword, filter),
            "listDataSourceInSearchedTables", dataSourceIds, keyword);
    }

    @Override
    @MethodMapping
    public ResultDTO<PaginatedResult<String>> searchName(List<Long> dataSourceIds,
                                                         String keyword,
                                                         TableQueryFilter filter) {
        return this.log(() -> this.tableService.searchName(dataSourceIds, keyword, filter),
            "searchName", dataSourceIds, keyword);
    }

    @Override
    @MethodMapping
    public ResultDTO<PaginatedResult<String>> searchName(Long dataSourceId, String keyword, TableQueryFilter filter) {
        return TableApi.super.searchName(dataSourceId, keyword, filter);
    }

    @Override
    @MethodMapping
    public ResultDTO<PaginatedResult<String>> listName(List<Long> dataSourceIds, TableQueryFilter filter) {
        return TableApi.super.listName(dataSourceIds, filter);
    }

    @Override
    @MethodMapping
    public ResultDTO<PaginatedResult<String>> listName(Long dataSourceId, TableQueryFilter filter) {
        return TableApi.super.listName(dataSourceId, filter);
    }
}

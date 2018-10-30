package com.fishqq.meta.rest;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.dt.dataphin.common.lang.result.ResultDTO;
import com.alibaba.dt.dataphin.meta.client.api.TableApi;
import com.alibaba.dt.dataphin.meta.client.common.PaginatedResult;
import com.alibaba.dt.dataphin.meta.client.model.table.Table;
import com.alibaba.dt.dataphin.meta.client.rest.request.TableRequest;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/9/13
 */
@RestController
@RequestMapping("/tables")
@Api(description = "tables", tags = {"[白路]table api"})
public class TableApiController {
    @Resource
    @Qualifier("rpc")
    private TableApi tableApi;

    @PostMapping(value = "/listByGuid")
    public ResultDTO<List<Table>> listByGuid(@RequestBody TableRequest request) {
        return this.tableApi.listByGuid(request.getTableGuids(), request.getFilter());
    }

    @PostMapping(value = "/getByGuid")
    public ResultDTO<Table> getByGuid(@RequestBody TableRequest request) {
        return this.tableApi.getByGuid(request.getTableGuids().get(0), request.getFilter());
    }

    @PostMapping(value = "/batchListByNames")
    public ResultDTO<List<Table>> batchListByNames(@RequestBody TableRequest request) {
        return this.tableApi.listByName(request.getDataSourceIds(), request.getTableNames(), request.getFilter());
    }

    @PostMapping(value = "/batchListByName")
    public ResultDTO<List<Table>> batchListByName(@RequestBody TableRequest request) {
        return this.tableApi.listByName(request.getDataSourceIds(), request.getTableNames().get(0),
            request.getFilter());
    }

    @PostMapping(value = "/listByNames")
    public ResultDTO<List<Table>> listByName(@RequestBody TableRequest request) {
        return this.tableApi.listByName(request.getDataSourceIds().get(0), request.getTableNames(),
            request.getFilter());
    }

    @PostMapping(value = "/getByName")
    public ResultDTO<Table> getByName(@RequestBody TableRequest request) {
        return this.tableApi.getByName(request.getDataSourceIds().get(0),
            request.getTableNames().get(0), request.getFilter());
    }

    @PostMapping(value = "/batchSearch")
    public ResultDTO<PaginatedResult<Table>> batchSearch(@RequestBody TableRequest request) {
        return this.tableApi.search(request.getDataSourceIds(), request.getKeyword(), request.getFilter());
    }

    @PostMapping(value = "/search")
    public ResultDTO<PaginatedResult<Table>> search(@RequestBody TableRequest request) {
        return this.tableApi.search(request.getDataSourceIds().get(0), request.getKeyword(), request.getFilter());
    }

    @PostMapping(value = "/batchList")
    public ResultDTO<PaginatedResult<Table>> batchList(@RequestBody TableRequest request) {
        return this.tableApi.list(request.getDataSourceIds(), request.getFilter());
    }

    @PostMapping(value = "/list")
    public ResultDTO<PaginatedResult<Table>> list(@RequestBody TableRequest request) {
        return this.tableApi.list(request.getDataSourceIds().get(0), request.getFilter());
    }

    @PostMapping(value = "/countSearchedTables")
    public ResultDTO<Long> countSearchedTables(@RequestBody TableRequest request) {
        return this.tableApi.countSearchedTables(request.getDataSourceIds(), request.getKeyword(), request.getFilter());
    }

    @PostMapping(value = "/batchCountTables")
    public ResultDTO<Long> batchCountTables(@RequestBody TableRequest request) {
        return this.tableApi.countTables(request.getDataSourceIds(), request.getFilter());
    }

    @PostMapping(value = "/countTables")
    public ResultDTO<Long> countTables(@RequestBody TableRequest request) {
        return this.tableApi.countTables(request.getDataSourceIds().get(0), request.getFilter());
    }

    @PostMapping(value = "/searchInCluster")
    public ResultDTO<PaginatedResult<Table>> searchInCluster(@RequestBody TableRequest request) {
        return this.tableApi.searchInCluster(request.getTenantId(), request.getDataSourceIds().get(0),
            request.getKeyword(), request.getFilter());
    }

    @PostMapping(value = "/listDataSourceInSearchedTables")
    public ResultDTO<List<Long>> listDataSourceInSearchedTables(@RequestBody TableRequest request) {
        return this.tableApi.listDataSourceInSearchedTables(request.getDataSourceIds(),
            request.getKeyword(), request.getFilter());
    }

    @PostMapping(value = "/batchSearchName")
    public ResultDTO<PaginatedResult<String>> batchSearchName(@RequestBody TableRequest request) {
        return this.tableApi.searchName(request.getDataSourceIds(), request.getKeyword(), request.getFilter());
    }

    @PostMapping(value = "/searchName")
    public ResultDTO<PaginatedResult<String>> searchName(@RequestBody TableRequest request) {
        return this.tableApi.searchName(request.getDataSourceIds().get(0), request.getKeyword(), request.getFilter());
    }

    @PostMapping(value = "/batchListName")
    public ResultDTO<PaginatedResult<String>> batchListName(@RequestBody TableRequest request) {
        return this.tableApi.listName(request.getDataSourceIds(), request.getFilter());
    }

    @PostMapping(value = "/listName")
    public ResultDTO<PaginatedResult<String>> listName(@RequestBody TableRequest request) {
        return this.tableApi.listName(request.getDataSourceIds().get(0), request.getFilter());
    }
}

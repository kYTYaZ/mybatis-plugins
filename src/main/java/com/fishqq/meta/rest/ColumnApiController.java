package com.fishqq.meta.rest;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.dt.dataphin.common.lang.result.ResultDTO;
import com.alibaba.dt.dataphin.meta.client.api.ColumnApi;
import com.alibaba.dt.dataphin.meta.client.model.column.Column;
import com.alibaba.dt.dataphin.meta.client.rest.request.ColumnRequest;

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
@RequestMapping("/columns")
@Api(description = "columns", tags = {"[白路]column api"})
public class ColumnApiController {
    @Resource
    @Qualifier("rpc")
    private ColumnApi columnApi;

    @PostMapping(value = "/batchListByTableGuid")
    public ResultDTO<Map<String, List<Column>>> batchListByTableGuid(@RequestBody ColumnRequest request) {
        return this.columnApi.listByTableGuid(request.getTableGuids(), request.getFilter());
    }

    @PostMapping(value = "/listByTableGuid")
    public ResultDTO<List<Column>> listByTableGuid(@RequestBody ColumnRequest request) {
        return this.columnApi.listByTableGuid(request.getTableGuids().get(0), request.getFilter());
    }

    @PostMapping(value = "/batchListByTableName")
    public ResultDTO<Map<String, List<Column>>> batchListByTableName(@RequestBody ColumnRequest request) {
        return this.columnApi.listByTableName(
            request.getDataSourceIds().get(0), request.getTableNames(), request.getFilter());
    }

    @PostMapping(value = "/listByTableName")
    public ResultDTO<List<Column>> listByTableName(@RequestBody ColumnRequest request) {
        return this.columnApi.listByTableName(
            request.getDataSourceIds().get(0), request.getTableNames().get(0), request.getFilter());
    }

    @PostMapping(value = "/batchCountByTableGuid")
    public ResultDTO<Map<String, Long>> batchCountByTableGuid(@RequestBody ColumnRequest request) {
        return this.columnApi.countByTableGuid(
            request.getTenantId(), request.getTableGuids(), request.getFilter().isNeedRealTime());
    }

    @PostMapping(value = "/countByTableGuid")
    public ResultDTO<Long> countByTableGuid(@RequestBody ColumnRequest request) {
        return this.columnApi.countByTableGuid(
            request.getTenantId(), request.getTableGuids().get(0), request.getFilter().isNeedRealTime());
    }

    @PostMapping(value = "/batchCountByTableName")
    public ResultDTO<Map<String, Long>> batchCountByTableName(@RequestBody ColumnRequest request) {
        return this.columnApi.countByTableName(
            request.getTenantId(), request.getDataSourceIds().get(0),
            request.getTableNames(), request.getFilter().isNeedRealTime());
    }

    @PostMapping(value = "/countByTableName")
    public ResultDTO<Long> countByTableName(@RequestBody ColumnRequest request) {
        return this.columnApi.countByTableName(
            request.getTenantId(), request.getDataSourceIds().get(0),
            request.getTableNames().get(0), request.getFilter().isNeedRealTime());
    }
}


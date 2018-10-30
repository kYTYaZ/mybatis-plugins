package com.fishqq.meta.rest;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.dt.dataphin.common.lang.result.ResultDTO;
import com.alibaba.dt.dataphin.meta.client.api.PartitionApi;
import com.alibaba.dt.dataphin.meta.client.common.PaginatedResult;
import com.alibaba.dt.dataphin.meta.client.model.column.Range;
import com.alibaba.dt.dataphin.meta.client.model.partition.Partition;
import com.alibaba.dt.dataphin.meta.client.rest.request.PartitionRequest;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
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
@RequestMapping("/partitions")
@Api(description = "partitions", tags = {"[白路]partition api"})
public class PartitionApiController {
    @Resource
    @Qualifier("rpc")
    private PartitionApi partitionApi;

    @PostMapping(value = "/batchListByTableGuid")
    public ResultDTO<PaginatedResult<Partition>> batchListByTableGuid(@RequestBody PartitionRequest request) {
        return this.partitionApi.listByTableGuid(request.getTableGuids(), request.getFilter());
    }

    @PostMapping(value = "/listByTableGuid")
    public ResultDTO<PaginatedResult<Partition>> listByTableGuid(@RequestBody PartitionRequest request) {
        return this.partitionApi.listByTableGuid(request.getTableGuids().get(0), request.getFilter());
    }

    @PostMapping(value = "/batchListByTableName")
    public ResultDTO<PaginatedResult<Partition>> batchListByTableName(@RequestBody PartitionRequest request) {
        return this.partitionApi.listByTableName(
            request.getDataSourceIds().get(0), request.getTableNames(), request.getFilter());
    }

    @PostMapping(value = "/listByTableName")
    public ResultDTO<PaginatedResult<Partition>> listByTableName(@RequestBody PartitionRequest request) {
        return this.partitionApi.listByTableName(
            request.getDataSourceIds().get(0), request.getTableNames().get(0), request.getFilter());
    }

    @PostMapping(value = "/batchListInRanges")
    public ResultDTO<List<Partition>> batchListInRanges(@RequestBody PartitionRequest request) {
        Multimap<String, Range<String>> ranges = ArrayListMultimap.create();
        for (int i = 0; i < request.getTableGuids().size(); i++) {
            ranges.put(request.getTableGuids().get(i), request.getRanges().get(i));
        }

        return this.partitionApi.listInRange(ranges, request.getFilter());
    }

    @PostMapping(value = "/batchListInRange")
    public ResultDTO<List<Partition>> batchListInRange(@RequestBody PartitionRequest request) {

        return this.partitionApi.listInRange(
            request.getTableGuids().get(0), request.getRanges().get(0), request.getFilter());
    }

    @PostMapping(value = "/listInRange")
    public ResultDTO<List<Partition>> listInRange(@RequestBody PartitionRequest request) {
        return this.partitionApi.listInRange(
            request.getTableGuids().get(0), request.getRanges().get(0), request.getFilter());
    }

    @PostMapping(value = "/getMinMax")
    public ResultDTO<List<Partition>> getMinMax(@RequestBody PartitionRequest request) {
        return this.partitionApi.getMinMax(request.getTableGuids().get(0), request.getFilter());
    }

    @PostMapping(value = "/batchCountByTableGuid")
    public ResultDTO<Map<String, Long>> batchCountByTableGuid(@RequestBody PartitionRequest request) {
        return this.partitionApi.countByTableGuid(
            request.getTenantId(), request.getTableGuids(), request.getFilter().isNeedRealTime());
    }

    @PostMapping(value = "/countByTableGuid")
    public ResultDTO<Long> countByTableGuid(@RequestBody PartitionRequest request) {
        return this.partitionApi.countByTableGuid(
            request.getTenantId(), request.getTableGuids().get(0), request.getFilter().isNeedRealTime());
    }

    @PostMapping(value = "/batchCountByTableName")
    public ResultDTO<Map<String, Long>> batchCountByTableName(@RequestBody PartitionRequest request) {
        return this.partitionApi.countByTableName(
            request.getTenantId(), request.getDataSourceIds().get(0), request.getTableNames(),
            request.getFilter().isNeedRealTime());
    }

    @PostMapping(value = "/countByTableName")
    public ResultDTO<Long> countByTableName(@RequestBody PartitionRequest request) {
        return this.partitionApi.countByTableName(
            request.getTenantId(), request.getDataSourceIds().get(0), request.getTableNames().get(0),
            request.getFilter().isNeedRealTime());

    }
}

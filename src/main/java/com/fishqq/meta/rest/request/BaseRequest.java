package com.alibaba.dt.dataphin.meta.client.rest.request;

import java.util.List;

/**
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/9/20
 */
public class BaseRequest {
    private Long tenantId;
    private String keyword;
    private List<Long> dataSourceIds;
    private List<String> dataSourceGuids;
    private List<String> tableNames;
    private List<String> tableGuids;

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<Long> getDataSourceIds() {
        return dataSourceIds;
    }

    public void setDataSourceIds(List<Long> dataSourceIds) {
        this.dataSourceIds = dataSourceIds;
    }

    public List<String> getDataSourceGuids() {
        return dataSourceGuids;
    }

    public void setDataSourceGuids(List<String> dataSourceGuids) {
        this.dataSourceGuids = dataSourceGuids;
    }

    public List<String> getTableNames() {
        return tableNames;
    }

    public void setTableNames(List<String> tableNames) {
        this.tableNames = tableNames;
    }

    public List<String> getTableGuids() {
        return tableGuids;
    }

    public void setTableGuids(List<String> tableGuids) {
        this.tableGuids = tableGuids;
    }
}

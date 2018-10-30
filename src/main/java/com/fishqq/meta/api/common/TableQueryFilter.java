/*
* Copyright 2017 Alibaba.com All right reserved. This software is the
* confidential and proprietary information of Alibaba.com ("Confidential
* Information"). You shall not disclose such Confidential Information and shall
* use it only in accordance with the terms of the license agreement you entered
* into with Alibaba.com.
*/
package com.fishqq.meta.api.common;

import java.io.Serializable;
import java.util.List;

import com.alibaba.dt.dataphin.common.lang.criteria.PaginationCriteria;

/**
 * 物理表查询筛选条件
 * （由于列信息和分区信息数量巨大，reload处理和传输耗费大量资源，影响性能，需要查询时必须显示指定，默认只查询表名）
 *
 * @author: bailu
 * @date: 2017/10/10 下午1:45
 */
public class TableQueryFilter implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 租户id
     */
    private Long tenantId;
    /**
     * 指定返回物理表Owner
     * (如果不设置，默认全部返回；如果指定，则返回指定Owner的表，其余表不返回)
     */
    private List<String> owners;
    /**
     * 逻辑项目
     */
    private List<Long> logicProjects;

    /**
     * 是否实时获取数据
     */
    private boolean needRealTime;

    /**
     * 需要查询返回列信息
     */
    private boolean needColumns;

    /**
     * 是否查询表扩展信息，默认只查询表名
     */
    private boolean needTableExt;
    /**
     * 需要表统计信息
     */
    private boolean needTableStat;

    /**
     * 是否需要列扩展信息
     */
    private boolean needColumnExt;
    /**
     * 需要列统计信息
     */
    private boolean needColumnStat;

    /**
     * 按前缀搜索
     */
    private boolean searchByPrefix;
    /**
     * 分页信息
     */
    private PaginationCriteria paginationCriteria;

    /**
     * 是否需要黑盒物理表：默认不需要黑盒物理表
     */
    private boolean needBbox;


    public Long getTenantId() {
        return tenantId;
    }

    public TableQueryFilter setTenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public boolean isNeedRealTime() {
        return needRealTime;
    }

    public TableQueryFilter setNeedRealTime(boolean needRealTime) {
        this.needRealTime = needRealTime;
        return this;
    }

    public List<String> getOwners() {
        return owners;
    }

    public TableQueryFilter setOwners(List<String> owners) {
        this.owners = owners;
        return this;
    }

    public List<Long> getLogicProjects() {
        return logicProjects;
    }

    public TableQueryFilter setLogicProjects(List<Long> logicProjects) {
        this.logicProjects = logicProjects;
        return this;
    }

    public boolean isNeedColumns() {
        return needColumns;
    }

    public TableQueryFilter setNeedColumns(boolean needColumns) {
        this.needColumns = needColumns;
        return this;
    }

    public boolean isNeedTableExt() {
        return needTableExt;
    }

    public TableQueryFilter setNeedTableExt(boolean needTableExt) {
        this.needTableExt = needTableExt;
        return this;
    }

    public boolean isNeedTableStat() {
        return needTableStat;
    }

    public TableQueryFilter setNeedTableStat(boolean needTableStat) {
        this.needTableStat = needTableStat;
        return this;
    }

    public boolean isNeedColumnExt() {
        return needColumnExt;
    }

    public TableQueryFilter setNeedColumnExt(boolean needColumnExt) {
        this.needColumnExt = needColumnExt;
        return this;
    }

    public boolean isNeedColumnStat() {
        return needColumnStat;
    }

    public TableQueryFilter setNeedColumnStat(boolean needColumnStat) {
        this.needColumnStat = needColumnStat;
        return this;
    }

    public boolean isNeedBbox() {
        return needBbox;
    }

    public TableQueryFilter setNeedBbox(boolean needBbox) {
        this.needBbox = needBbox;
        return this;
    }

    public boolean isSearchByPrefix() {
        return searchByPrefix;
    }

    public TableQueryFilter setSearchByPrefix(boolean searchByPrefix) {
        this.searchByPrefix = searchByPrefix;
        return this;
    }

    public PaginationCriteria getPaginationCriteria() {
        return paginationCriteria;
    }

    public TableQueryFilter setPaginationCriteria(
        PaginationCriteria paginationCriteria) {
        this.paginationCriteria = paginationCriteria;
        return this;
    }
}
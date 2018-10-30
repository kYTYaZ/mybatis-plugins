/*
* Copyright 2017 Alibaba.com All right reserved. This software is the
* confidential and proprietary information of Alibaba.com ("Confidential
* Information"). You shall not disclose such Confidential Information and shall
* use it only in accordance with the terms of the license agreement you entered
* into with Alibaba.com.
*/
package com.fishqq.meta.models;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.alibaba.dt.onedata.datasource.DataSourceTypeEnum;

/**
 * @author: bailu
 * @date: 2017/9/27 上午11:26
 */
public class TableBO {
    /**
     * data_source_guid.table_name
     */
    private String guid;

    /**
     * 数据源GUID
     */
    private String dataSourceGuid;

    /**
     * 数据源类型
     */
    private DataSourceTypeEnum dataSourceType;

    /**
     * 表名
     */
    private String name;

    /**
     * 显示名
     */
    private String displayName;

    /**
     * 是否为分区表：Y／N
     */
    private Boolean isPartitioned;

    /**
     * 表描述信息
     */
    private String comment;

    /**
     * 表Owner
     */
    private String owner;

    /**
     * 表创建者
     */
    private String creator;

    /**
     * 表创建时间
     */
    private Date createTime;

    /**
     * 表DDL最后变更时间
     */
    private Date lastDdlTime;

    /**
     * 表最后数据变更时间
     */
    private Date lastDmlTime;

    /**
     * 表最后数据查看时间
     */
    private Date lastAccessTime;

    /**
     * 生命周期时间
     */
    private Long lifeCycleTime;

    /**
     * 数据量
     */
    private Long dataSize;

    /**
     * DS分区最大值
     */
    private String maxDS;

    /**
     * DS分区最小值
     */
    private String minDS;

    /**
     * 是否是视图
     */
    private Boolean isView;

    /**
     * 逻辑项目名
     */
    private String logicProjectName;

    /**
     * 逻辑项目id
     */
    private Long logicProjectId;

    /**
     * 列信息
     */
    private List<ColumnBO> columns;

    /**
     * 分区信息
     */
    private List<PartitionBO> partitions;

    /**
     * 数据更新时间
     */
    private Date syncTime;

    /**
     * 存储的文件路径, 目前只有HIVE的物理表有该属性
     */
    private String location;

    /**
     * 文件类型, 目前只有HIVE的物理表有该属性
     */
    private String fileType;

    /**
     * 最近3个月访问次数
     */
    private Long visitCount30d;

    /**
     * 最近30天读取次数
     */
    private Long queryCount30d;

    /**
     * 字段个数
     */
    /**
     * 字段个数
     */
    private Integer columnCount;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getDataSourceGuid() {
        return dataSourceGuid;
    }

    public void setDataSourceGuid(String dataSourceGuid) {
        this.dataSourceGuid = dataSourceGuid;
    }

    public DataSourceTypeEnum getDataSourceType() {
        return dataSourceType;
    }

    public void setDataSourceType(DataSourceTypeEnum dataSourceType) {
        this.dataSourceType = dataSourceType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Boolean isPartitioned() {
        return isPartitioned;
    }

    public void setPartitioned(Boolean partitioned) {
        isPartitioned = partitioned;
    }

    public Boolean isView() {
        return isView;
    }

    public void setView(Boolean view) {
        isView = view;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastDdlTime() {
        return lastDdlTime;
    }

    public void setLastDdlTime(Date lastDdlTime) {
        this.lastDdlTime = lastDdlTime;
    }

    public Date getLastDmlTime() {
        return lastDmlTime;
    }

    public void setLastDmlTime(Date lastDmlTime) {
        this.lastDmlTime = lastDmlTime;
    }

    public Date getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Date lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public Long getLifeCycleTime() {
        return lifeCycleTime;
    }

    public void setLifeCycleTime(Long lifeCycleTime) {
        this.lifeCycleTime = lifeCycleTime;
    }

    public Long getDataSize() {
        return dataSize;
    }

    public void setDataSize(Long dataSize) {
        this.dataSize = dataSize;
    }

    public String getMaxDS() {
        return maxDS;
    }

    public void setMaxDS(String maxDS) {
        this.maxDS = maxDS;
    }

    public String getMinDS() {
        return minDS;
    }

    public void setMinDS(String minDS) {
        this.minDS = minDS;
    }

    public String getLogicProjectName() {
        return logicProjectName;
    }

    public void setLogicProjectName(String logicProjectName) {
        this.logicProjectName = logicProjectName;
    }

    public Long getLogicProjectId() {
        return logicProjectId;
    }

    public void setLogicProjectId(Long logicProjectId) {
        this.logicProjectId = logicProjectId;
    }

    public Integer getColumnCount() {
        if (this.columnCount != null && this.columnCount > 0) {
            return this.columnCount;
        } else {
            return this.columns == null ? 0 : this.columns.size();
        }
    }

    public void setColumnCount(Integer columnCount) {
        this.columnCount = columnCount;
    }

    public List<ColumnBO> getColumns() {
        return Optional.ofNullable(columns).orElse(Collections.emptyList());
    }

    public void setColumns(List<ColumnBO> columns) {
        this.columns = columns;
    }

    public List<PartitionBO> getPartitions() {
        return Optional.ofNullable(partitions).orElse(Collections.emptyList());
    }

    public void setPartitions(List<PartitionBO> partitions) {
        this.partitions = partitions;
    }

    public Date getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(Date syncTime) {
        this.syncTime = syncTime;
    }

    public Long getVisitCount30d() {
        return visitCount30d;
    }

    public void setVisitCount30d(Long visitCount30d) {
        this.visitCount30d = visitCount30d;
    }

    public Long getQueryCount30d() {
        return queryCount30d;
    }

    public void setQueryCount30d(Long queryCount30d) {
        this.queryCount30d = queryCount30d;
    }
}
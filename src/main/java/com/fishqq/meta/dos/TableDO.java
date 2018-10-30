/*
* Copyright 2017 Alibaba.com All right reserved. This software is the
* confidential and proprietary information of Alibaba.com ("Confidential
* Information"). You shall not disclose such Confidential Information and shall
* use it only in accordance with the terms of the license agreement you entered
* into with Alibaba.com.
*/
package com.fishqq.meta.dos;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

import com.alibaba.dt.dataphin.common.mybatis.interceptor.sequence.SequenceObject;

/**
 * 物理Table
 *
 * @author: bailu
 * @date: 2017/9/27 上午11:26
 */
public class TableDO implements SequenceObject, Serializable{
    private static final long serialVersionUID = 1L;

    /**
     * 表ID
     */
    private Long id;

    /**
     * 在元仓中的id
     */
    private String originId;

    /**
     * 全局唯一ID ds_guid.table_name
     */
    private String guid;

    /**
     * 数据源GUID
     */
    private String dataSourceGuid;

    /**
     * 数据源类型
     */
    private String dataSourceType;

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
     * 是否是视图：Y／N
     */
    private Boolean isView;

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
     * 数据量
     */
    private Long dataSize;

    /**
     * 生命周期时间
     */
    private Long lifeCycleTime;

    /**
     * 逻辑项目id
     */
    private Long logicProjectId;

    /**
     * 逻辑表名
     */
    private String logicProjectName;

    /**
     * 同步来源
     */
    private String syncSource;

    /**
     * "存储的文件路径, 目前只有HIVE的物理表有该属性"
     */
    private String location;

    /**
     * "文件类型, 目前只有HIVE的物理表有该属性"
     */
    private String fileType;

    /**
     * 30天访问次数
     */
    private Long visitCount30d;

    /**
     * 30天查询次数
     */
    private Long queryCount30d;

    /**
     * 是否是黑盒物理表
     */
    private Boolean isBbox;

    private Integer status;

    private Integer syncStatus;

    private Date syncTime;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public void setOriginIntId(Long originId) {
        this.originId = originId == null ? null : originId.toString();
    }

    public Date getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(Date syncTime) {
        this.syncTime = syncTime;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isPartitioned() {
        return Optional.ofNullable(isPartitioned).orElse(false);
    }

    public void setPartitioned(Boolean partitioned) {
        isPartitioned = partitioned;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(Integer syncStatus) {
        this.syncStatus = syncStatus;
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

    public Long getDataSize() {
        return dataSize;
    }

    public void setDataSize(Long dataSize) {
        this.dataSize = dataSize;
    }

    public Long getLifeCycleTime() {
        return lifeCycleTime;
    }

    public void setLifeCycleTime(Long lifeCycleTime) {
        this.lifeCycleTime = lifeCycleTime;
    }

    public Boolean isView() {
        return Optional.ofNullable(isView).orElse(false);
    }

    public void setView(Boolean view) {
        isView = view;
    }

    public Long getLogicProjectId() {
        return logicProjectId;
    }

    public void setLogicProjectId(Long logicProjectId) {
        this.logicProjectId = logicProjectId;
    }

    public String getLogicProjectName() {
        return logicProjectName;
    }

    public void setLogicProjectName(String logicProjectName) {
        this.logicProjectName = logicProjectName;
    }

    public String getSyncSource() {
        return syncSource;
    }

    public void setSyncSource(String syncSource) {
        this.syncSource = syncSource;
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

    public String getDataSourceType() {
        return dataSourceType;
    }

    public void setDataSourceType(String dataSourceType) {
        this.dataSourceType = dataSourceType;
    }

    public Long getVisitCount30d() {
        return Optional.ofNullable(visitCount30d).orElse(0L);
    }

    public void setVisitCount30d(Long visitCount30d) {
        this.visitCount30d = visitCount30d;
    }

    public Long getQueryCount30d() {
        return Optional.ofNullable(queryCount30d).orElse(0L);
    }

    public void setQueryCount30d(Long queryCount30d) {
        this.queryCount30d = queryCount30d;
    }

    public Boolean isBbox() {
        return isBbox;
    }

    public void setBbox(Boolean bbox) {
        isBbox = bbox;
    }

    @Override
    public String toString() {
        return "TableDO{" +
            "id=" + id +
            ", guid='" + guid + '\'' +
            ", dataSourceGuid='" + dataSourceGuid + '\'' +
            ", dataSourceType='" + dataSourceType + '\'' +
            ", name='" + name + '\'' +
            ", isPartitioned=" + isPartitioned +
            ", isView=" + isView +
            ", comment='" + comment + '\'' +
            ", owner='" + owner + '\'' +
            ", creator='" + creator + '\'' +
            ", createTime=" + createTime +
            ", lastDdlTime=" + lastDdlTime +
            ", lastDmlTime=" + lastDmlTime +
            ", lastAccessTime=" + lastAccessTime +
            ", dataSize=" + dataSize +
            ", lifeCycleTime=" + lifeCycleTime +
            ", logicProjectName='" + logicProjectName + '\'' +
            ", syncSource='" + syncSource + '\'' +
            ", location='" + location + '\'' +
            ", fileType='" + fileType + '\'' +
            ", visitCount30d=" + visitCount30d +
            ", queryCount30d=" + queryCount30d +
            ", isBbox=" + isBbox +
            '}';
    }
}
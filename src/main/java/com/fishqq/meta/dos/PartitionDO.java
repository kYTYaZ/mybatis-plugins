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

import com.alibaba.dt.dataphin.common.mybatis.interceptor.sequence.SequenceObject;

/**
 * 物理分区
 *
 * @author: bailu
 * @date: 2017/9/27 上午11:26
 */
public class PartitionDO implements SequenceObject, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增ID
     */
    private Long id;

    /**
     * 唯一建odps.project_name.table_name.partition
     * */
    private String guid;

    /**
     * dataSourceGuid
     */
    private String dataSourceGuid;

    /**
     * dataSourceType
     */
    private String dataSourceType;

    /**
     * tableGuid
     * */
    private String tableGuid;

    /**
     * 分区名
     * */
    private String name;

    /**
     * 类型
     * */
    private String type;

    /**
     * 创建时间
     * */
    private Date createTime;

    /**
     * 字段修改时间
     * */
    private Date modifyTime;

    /**
     * 数据量
     * */
    private Long dataSize;

    /**
     * 记录数
     * */
    private Long records;

    /**
     * 同步时间
     * */
    private Date syncTime;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getTableGuid() {
        return tableGuid;
    }

    public void setTableGuid(String tableGuid) {
        this.tableGuid = tableGuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setCreateTimeSeconds(Long createTimeSeconds) {
        this.createTime = new Date(createTimeSeconds * 1000);
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Long getDataSize() {
        return dataSize;
    }

    public void setDataSize(Long dataSize) {
        this.dataSize = dataSize;
    }

    public Long getRecords() {
        return records;
    }

    public void setRecords(Long records) {
        this.records = records;
    }

    public Date getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(Date syncTime) {
        this.syncTime = syncTime;
    }

    public String getDataSourceGuid() {
        return dataSourceGuid;
    }

    public void setDataSourceGuid(String dataSourceGuid) {
        this.dataSourceGuid = dataSourceGuid;
    }

    public String getDataSourceType() {
        return dataSourceType;
    }

    public void setDataSourceType(String dataSourceType) {
        this.dataSourceType = dataSourceType;
    }

    @Override
    public String toString() {
        return "PartitionDO{" +
            "id=" + id +
            ", guid='" + guid + '\'' +
            ", dataSourceGuid='" + dataSourceGuid + '\'' +
            ", dataSourceType='" + dataSourceType + '\'' +
            ", tableGuid='" + tableGuid + '\'' +
            ", name='" + name + '\'' +
            ", type='" + type + '\'' +
            ", createTime=" + createTime +
            ", modifyTime=" + modifyTime +
            ", dataSize=" + dataSize +
            ", records=" + records +
            ", syncTime=" + syncTime +
            '}';
    }
}
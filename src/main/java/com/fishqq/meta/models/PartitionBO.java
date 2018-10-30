/*
* Copyright 2017 Alibaba.com All right reserved. This software is the
* confidential and proprietary information of Alibaba.com ("Confidential
* Information"). You shall not disclose such Confidential Information and shall
* use it only in accordance with the terms of the license agreement you entered
* into with Alibaba.com.
*/
package com.fishqq.meta.models;

import java.util.Date;

import com.alibaba.dt.onedata.datasource.DataSourceTypeEnum;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author: bailu
 * @date: 2017/9/27 上午11:26
 */
public class PartitionBO {
    /**
     * 分区名和分区值列表：解析ds=20180303/hour=01/min=02
     */
    private String name;

    /**
     * table_guid.name
     */
    private String guid;

    /**
     * table guid
     */
    private String tableGuid;

    private String dataSourceGuid;

    private DataSourceTypeEnum dataSourceType;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 数据量
     */
    private Long dataSize;

    /**
     * 记录数
     */
    private Long records;

    public DataSourceTypeEnum getDataSourceType() {
        return dataSourceType;
    }

    public void setDataSourceType(DataSourceTypeEnum dataSourceType) {
        this.dataSourceType = dataSourceType;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
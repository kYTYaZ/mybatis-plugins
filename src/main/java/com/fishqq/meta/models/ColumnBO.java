/*
* Copyright 2017 Alibaba.com All right reserved. This software is the
* confidential and proprietary information of Alibaba.com ("Confidential
* Information"). You shall not disclose such Confidential Information and shall
* use it only in accordance with the terms of the license agreement you entered
* into with Alibaba.com.
*/
package com.fishqq.meta.models;

import java.util.Date;

import com.alibaba.dt.dataphin.meta.client.enums.ColumnTypeEnum;
import com.alibaba.dt.onedata.datasource.DataSourceTypeEnum;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author: bailu
 * @date: 2017/9/27 上午11:26
 */
public class ColumnBO {
    private String guid;

    private String dataSourceGuid;

    private DataSourceTypeEnum dataSourceType;

    private String tableGuid;

    private String name;

    private Integer seqNumber;

    private String displayName;

    private String comment;

    private String dataType;

    private ColumnTypeEnum dataTypeEnum;

    private Boolean isAllowEmpty;

    private String defaultValue;

    private Boolean isAutoInc;

    private Boolean isPk;

    private Boolean isFk;

    private Boolean isPt;

    private Date createTime;

    private Date modifyTime;

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

    /**
     * 热度值
     */
    private Long queryCount30d;

    public Long getQueryCount30d() {
        return queryCount30d;
    }

    public void setQueryCount30d(Long queryCount30d) {
        this.queryCount30d = queryCount30d;
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

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ColumnTypeEnum getDataTypeEnum() {
        return dataTypeEnum;
    }

    public void setDataTypeEnum(ColumnTypeEnum dataTypeEnum) {
        this.dataTypeEnum = dataTypeEnum;
    }

    public Integer getSeqNumber() {
        return seqNumber;
    }

    public void setSeqNumber(Integer seqNumber) {
        this.seqNumber = seqNumber;
    }

    public Boolean isAllowEmpty() {
        return isAllowEmpty;
    }

    public void setAllowEmpty(Boolean allowEmpty) {
        isAllowEmpty = allowEmpty;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Boolean isAutoInc() {
        return isAutoInc;
    }

    public void setAutoInc(Boolean autoInc) {
        isAutoInc = autoInc;
    }

    public Boolean isPk() {
        return isPk;
    }

    public void setPk(Boolean pk) {
        isPk = pk;
    }

    public Boolean isFk() {
        return isFk;
    }

    public void setFk(Boolean fk) {
        isFk = fk;
    }

    public void setPt(Boolean pt) {
        isPt = pt;
    }

    public Boolean isPt() {
        return this.isPt;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
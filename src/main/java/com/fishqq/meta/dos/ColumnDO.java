/*
* Copyright 2017 Alibaba.com All right reserved. This software is the
* confidential and proprietary information of Alibaba.com ("Confidential
* Information"). You shall not disclose such Confidential Information and shall
* use it only in accordance with the terms of the license agreement you entered
* into with Alibaba.com.
*/
package com.fishqq.meta.dos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Optional;

import com.alibaba.dt.dataphin.common.mybatis.interceptor.sequence.SequenceObject;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author: bailu
 * @date: 2017/9/27 上午11:26
 */
public class ColumnDO implements SequenceObject, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 列ID
     */
    private Long id;

    private String originId;

    /**
     * guid
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
     * 表guid
     */
    private String tableGuid;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 字段名
     */
    private String name;

    /**
     * 中文名
     */
    private String displayName;

    /**
     * 字段备注
     */
    private String comment;

    /**
     * 字段数据类型
     */
    private String dataType;

    /**
     * 字段排列顺序
     */
    private Integer seqNumber;

    /**
     * 是否允许为空
     */
    private Boolean isAllowEmpty;

    /**
     * 字段默认值
     */
    private String defaultValue;

    /**
     * 是否自增，Y/N
     */
    private Boolean isAutoInc;

    /**
     * 是否主键
     */
    private Boolean isPk;

    /**
     * 是否外键
     */
    private Boolean isFk;

    /**
     * 是否分区字段 Y/N
     */
    private Boolean isPt;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 字段修改时间
     */
    private Date modifyTime;

    private Long queryCount30d;

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

    public String getDataSourceType() {
        return dataSourceType;
    }

    public void setDataSourceType(String dataSourceType) {
        this.dataSourceType = dataSourceType;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
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

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Integer getSeqNumber() {
        return seqNumber;
    }

    public void setSeqNumber(Long seqNumber) {
        if (seqNumber != null) {
            this.seqNumber = seqNumber.intValue();
        }
    }

    public void setSeqNumber(BigInteger seqNumber) {
        if (seqNumber != null) {
            this.seqNumber = seqNumber.intValue();
        }
    }

    public void setSeqNumber(BigDecimal seqNumber) {
        if (seqNumber != null) {
            this.seqNumber = seqNumber.intValue();
        }
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
        return Optional.ofNullable(isAutoInc).orElse(false);
    }

    public void setAutoInc(Boolean autoInc) {
        isAutoInc = autoInc;
    }

    public Boolean isPk() {
        return Optional.ofNullable(isPk).orElse(false);
    }

    public void setPk(Boolean pk) {
        isPk = pk;
    }

    public Boolean isFk() {
        return Optional.ofNullable(isFk).orElse(false);
    }

    public void setFk(Boolean fk) {
        isFk = fk;
    }



    public Boolean seqNumber() {
        return Optional.ofNullable(isPt).orElse(false);
    }

    public void setPt(Boolean pt) {
        isPt = pt;
    }

    public Boolean isPt() {
        return isPt;
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

    public Long getQueryCount30d() {
        return Optional.ofNullable(queryCount30d).orElse(0L);
    }

    public void setQueryCount30d(Long queryCount30d) {
        this.queryCount30d = queryCount30d;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }
}
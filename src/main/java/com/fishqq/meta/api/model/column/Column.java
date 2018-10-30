package com.fishqq.meta.api.model.column;

import java.io.Serializable;

/**
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/9/13
 */
public class Column implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 字段guid，table_guid.column_name
     */
    private String guid;

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
     * 字段中文名
     */
    private String displayName;

    /**
     * 字段备注
     */
    private String comment;

    /**
     * 原始类型
     */
    private String dataType;

    /**
     * 字段排列顺序
     */
    private Integer seqNumber;

    /**
     * 扩展信息
     */
    private ExtData ext;

    /**
     * 统计信息
     */
    private StatData stat;

    public String getGuid() {
        return guid;
    }

    public Column setGuid(String guid) {
        this.guid = guid;
        return this;
    }

    public String getTableGuid() {
        return tableGuid;
    }

    public Column setTableGuid(String tableGuid) {
        this.tableGuid = tableGuid;
        return this;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getName() {
        return name;
    }

    public Column setName(String name) {
        this.name = name;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Column setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public Column setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public String getDataType() {
        return dataType;
    }

    public Column setDataType(String dataType) {
        this.dataType = dataType;
        return this;
    }

    public Integer getSeqNumber() {
        return seqNumber;
    }

    public Column setSeqNumber(Integer seqNumber) {
        this.seqNumber = seqNumber;
        return this;
    }

    public ExtData getExt() {
        return ext;
    }

    public Column setExt(ExtData ext) {
        this.ext = ext;
        return this;
    }

    public StatData getStat() {
        return stat;
    }

    public Column setStat(StatData stat) {
        this.stat = stat;
        return this;
    }
}

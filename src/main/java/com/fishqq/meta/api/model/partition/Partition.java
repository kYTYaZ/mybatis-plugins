package com.fishqq.meta.api.model.partition;

import java.io.Serializable;

/**
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/9/13
 */
public class Partition implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 分区名和分区值列表：解析ds=20180303/hour=01/min=02
     */
    private String name;

    /**
     * table_guid.name
     */
    private String guid;

    private String tableName;

    private String tableGuid;

    private ExtData ext;

    private StatData stat;

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

    public ExtData getExt() {
        return ext;
    }

    public Partition setExt(ExtData ext) {
        this.ext = ext;
        return this;
    }

    public StatData getStat() {
        return stat;
    }

    public Partition setStat(StatData stat) {
        this.stat = stat;
        return this;
    }
}

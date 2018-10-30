package com.fishqq.meta.api.model.table;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/9/13
 */
public class ExtData implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 字段个数
     */
    private Integer columnCount;

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
     * 是否为分区表：Y／N
     */
    private Boolean isPartitioned;

    /**
     * 存储的文件路径, 目前只有HIVE的物理表有该属性
     */
    private String location;

    /**
     * 文件类型, 目前只有HIVE的物理表有该属性
     */
    private String fileType;

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

    public Integer getColumnCount() {
        return columnCount;
    }

    public ExtData setColumnCount(Integer columnCount) {
        this.columnCount = columnCount;
        return this;
    }

    public Date getLastDdlTime() {
        return lastDdlTime;
    }

    public ExtData setLastDdlTime(Date lastDdlTime) {
        this.lastDdlTime = lastDdlTime;
        return this;
    }

    public Date getLastDmlTime() {
        return lastDmlTime;
    }

    public ExtData setLastDmlTime(Date lastDmlTime) {
        this.lastDmlTime = lastDmlTime;
        return this;
    }

    public Date getLastAccessTime() {
        return lastAccessTime;
    }

    public ExtData setLastAccessTime(Date lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
        return this;
    }

    public Long getLifeCycleTime() {
        return lifeCycleTime;
    }

    public ExtData setLifeCycleTime(Long lifeCycleTime) {
        this.lifeCycleTime = lifeCycleTime;
        return this;
    }

    public Boolean getPartitioned() {
        return isPartitioned;
    }

    public ExtData setPartitioned(Boolean partitioned) {
        isPartitioned = partitioned;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public ExtData setLocation(String location) {
        this.location = location;
        return this;
    }

    public String getFileType() {
        return fileType;
    }

    public ExtData setFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public String getOwner() {
        return owner;
    }

    public ExtData setOwner(String owner) {
        this.owner = owner;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public ExtData setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ExtData setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
}

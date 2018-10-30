package com.fishqq.meta.api.model.table;

import java.io.Serializable;
import java.util.List;

import com.fishqq.meta.api.model.column.Column;

/**
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/9/13
 */
public class Table implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 数据源GUID 写入时可为空 ，会根据环境参数设置
     */
    private String dataSourceGuid;

    /**
     * 全局唯一ID，表示方式：datasource_guid.name
     */
    private String guid;

    /**
     * 表名
     */
    private String name;

    /**
     * 显示名
     */
    private String displayName;

    /**
     * 表描述信息
     */
    private String comment;

    /**
     * 是否是视图：Y／N
     */
    private Boolean isView;

    /**
     * 逻辑项目英文名
     */
    private String logicProjectName;

    /**
     * 逻辑项目id
     */
    private Long logicProjectId;

    /**
     * 列信息
     */
    private List<Column> columns;

    /**
     * 扩展信息
     */
    private ExtData ext;

    /**
     * 统计信息
     */
    private StatData stat;

    public String getDataSourceGuid() {
        return dataSourceGuid;
    }

    public Table setDataSourceGuid(String dataSourceGuid) {
        this.dataSourceGuid = dataSourceGuid;
        return this;
    }

    public String getGuid() {
        return guid;
    }

    public Table setGuid(String guid) {
        this.guid = guid;
        return this;
    }

    public String getName() {
        return name;
    }

    public Table setName(String name) {
        this.name = name;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public Table setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Boolean getView() {
        return isView;
    }

    public Table setView(Boolean view) {
        isView = view;
        return this;
    }

    public String getLogicProjectName() {
        return logicProjectName;
    }

    public Table setLogicProjectName(String logicProjectName) {
        this.logicProjectName = logicProjectName;
        return this;
    }

    public Long getLogicProjectId() {
        return logicProjectId;
    }

    public Table setLogicProjectId(Long logicProjectId) {
        this.logicProjectId = logicProjectId;
        return this;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public Table setColumns(List<Column> columns) {
        this.columns = columns;
        return this;
    }

    public ExtData getExt() {
        return ext;
    }

    public Table setExt(ExtData ext) {
        this.ext = ext;
        return this;
    }

    public StatData getStat() {
        return stat;
    }

    public Table setStat(StatData stat) {
        this.stat = stat;
        return this;
    }
}

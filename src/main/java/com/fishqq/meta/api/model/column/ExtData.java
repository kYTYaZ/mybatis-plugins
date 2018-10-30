package com.fishqq.meta.api.model.column;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/9/13
 */
public class ExtData implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 是否允许为空
     * */
    private Boolean isAllowEmpty;

    /**
     * 字段默认值
     * */
    private String defaultValue;

    /**
     * 是否自增，Y/N
     * */
    private Boolean isAutoInc;

    /**
     * 是否主键
     * */
    private Boolean isPk;

    /**
     * 是否外键
     * */
    private Boolean isFk;

    /**
     * 是否分区字段 Y/N
     * */
    private Boolean isPt;

    /**
     * 创建时间
     * */
    private Date createTime;

    /**
     * 字段修改时间
     * */
    private Date modifyTime;

    public Boolean getAllowEmpty() {
        return isAllowEmpty;
    }

    public ExtData setAllowEmpty(Boolean allowEmpty) {
        isAllowEmpty = allowEmpty;
        return this;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public ExtData setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public Boolean getAutoInc() {
        return isAutoInc;
    }

    public ExtData setAutoInc(Boolean autoInc) {
        isAutoInc = autoInc;
        return this;
    }

    public Boolean isPk() {
        return isPk;
    }

    public ExtData setPk(Boolean Pk) {
        isPk = Pk;
        return this;
    }

    public Boolean isFk() {
        return isFk;
    }

    public ExtData setFk(Boolean Fk) {
        isFk = Fk;
        return this;
    }

    public Boolean isPt() {
        return isPt;
    }

    public void setPt(Boolean pt) {
        isPt = pt;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ExtData setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public ExtData setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
        return this;
    }
}

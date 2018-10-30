package com.fishqq.meta.api.model.column;

import java.io.Serializable;

/**
 * 列值
 *
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/3/26
 */
public class ColumnValue implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public ColumnValue setValue(String value) {
        this.value = value;
        return this;
    }
}

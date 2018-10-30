package com.fishqq.meta.models;

/**
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/3/26
 */
public class ColumnValueBO {
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

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ColumnValueBO{" +
            "name='" + name + '\'' +
            ", value='" + value + '\'' +
            '}';
    }
}

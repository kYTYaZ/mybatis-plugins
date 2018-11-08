package com.fishqq.mybatis.plugins.pagination;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = PaginatedListSerializer.class)
public class PaginatedList<T> extends ArrayList<T> {
    private int total;

    public PaginatedList(List<T> data, int total) {
        super(data);
        this.total = total;
    }

    public PaginatedList() {
    }

    public Integer getTotal() {
        return total;
    }

    public PaginatedList setTotal(Integer total) {
        this.total = total;
        return this;
    }

    public void setData(List<T> data) {
        super.clear();
        super.addAll(data);
    }
}

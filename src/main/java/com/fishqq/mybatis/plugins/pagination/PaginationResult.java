package com.fishqq.mybatis.plugins.pagination;

import java.util.ArrayList;
import java.util.List;

public class PaginationResult<T> extends ArrayList<T> {
    private int totalCount;

    public PaginationResult(List<T> data, int totalCount) {
        super(data);
        this.totalCount = totalCount;
    }

    public PaginationResult() {
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public PaginationResult setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    public void setData(List<T> data) {
        super.clear();
        super.addAll(data);
    }
}

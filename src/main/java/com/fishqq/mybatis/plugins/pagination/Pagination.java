package com.fishqq.mybatis.plugins.pagination;

import java.io.Serializable;
import java.util.Optional;

public class Pagination implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final Integer DEFAULT_PAGE_SIZE = 10;

    private Integer page;

    private Integer pageSize;

    private boolean needCount;

    public Integer getPage() {
        return Optional.ofNullable(page).orElse(-1);
    }

    public Pagination setPage(Integer page) {
        this.page = page;
        return this;
    }

    public Integer getOffset() {
        return getPage() <= 0 ? 0 : (page - 1) * pageSize;
    }

    public Integer getPageSize() {
        return pageSize == null || pageSize <= 0 ? DEFAULT_PAGE_SIZE : pageSize;
    }

    public Pagination setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public boolean isNeedCount() {
        return needCount;
    }

    public Pagination setNeedCount(boolean needCount) {
        this.needCount = needCount;
        return this;
    }
}

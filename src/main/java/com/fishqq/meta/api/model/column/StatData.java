package com.fishqq.meta.api.model.column;

import java.io.Serializable;

/**
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/9/13
 */
public class StatData implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 热度值
     */
    private Long queryCount30d;

    public Long getQueryCount30d() {
        return queryCount30d;
    }

    public StatData setQueryCount30d(Long queryCount30d) {
        this.queryCount30d = queryCount30d;
        return this;
    }
}

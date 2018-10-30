package com.fishqq.meta.api.model.table;

import java.io.Serializable;

/**
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/9/13
 */
public class StatData implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 数据量
     */
    private Long dataSize;

    /**
     * 最近3个月访问次数
     */
    private Long visitCount30d;

    /**
     * 最近30天读取次数
     */
    private Long queryCount30d;

    public Long getDataSize() {
        return dataSize;
    }

    public StatData setDataSize(Long dataSize) {
        this.dataSize = dataSize;
        return this;
    }

    public Long getVisitCount30d() {
        return visitCount30d;
    }

    public StatData setVisitCount30d(Long visitCount30d) {
        this.visitCount30d = visitCount30d;
        return this;
    }

    public Long getQueryCount30d() {
        return queryCount30d;
    }

    public StatData setQueryCount30d(Long queryCount30d) {
        this.queryCount30d = queryCount30d;
        return this;
    }
}

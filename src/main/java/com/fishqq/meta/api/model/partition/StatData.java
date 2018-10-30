package com.fishqq.meta.api.model.partition;

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
     * 记录数
     */
    private Long records;

    public Long getDataSize() {
        return dataSize;
    }

    public StatData setDataSize(Long dataSize) {
        this.dataSize = dataSize;
        return this;
    }

    public Long getRecords() {
        return records;
    }

    public StatData setRecords(Long records) {
        this.records = records;
        return this;
    }
}

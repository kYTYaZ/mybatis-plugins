package com.fishqq.meta.api.model.column;

import java.io.Serializable;

/**
 * 列值区间
 *
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/3/26
 */
public class Range<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 最小值
     */
    private T min;

    /**
     * 最大值
     */
    private T max;

    public Range() {

    }

    public Range(T min, T max){
        this.min = min;
        this.max = max;
    }

    public T getMin() {
        return min;
    }

    public Range setMin(T min) {
        this.min = min;
        return this;
    }

    public T getMax() {
        return max;
    }

    public Range setMax(T max) {
        this.max = max;
        return this;
    }
}

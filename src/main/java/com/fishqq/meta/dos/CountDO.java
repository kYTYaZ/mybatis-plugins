package com.fishqq.meta.dos;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/3/6
 */
public class CountDO {
    private String key;

    /**
     * 数目
     */
    private Long count;

    public Long getCount() {
        return Optional.ofNullable(this.count).orElse(0L);
    }

    public void setCount(Integer count) {
        this.count = count.longValue();
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public void setCount(BigDecimal count) {
        this.count = count.longValue();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

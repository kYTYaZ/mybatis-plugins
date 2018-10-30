package com.fishqq.meta.api.common;

/**
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/9/17
 */
public class ColumnQueryFilter {
    private Long tenantId;
    private boolean needExt;
    private boolean needStat;
    private boolean needRealTime;

    public Long getTenantId() {
        return tenantId;
    }

    public ColumnQueryFilter setTenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public boolean isNeedExt() {
        return needExt;
    }

    public ColumnQueryFilter setNeedExt(boolean needExt) {
        this.needExt = needExt;
        return this;
    }

    public boolean isNeedStat() {
        return needStat;
    }

    public ColumnQueryFilter setNeedStat(boolean needStat) {
        this.needStat = needStat;
        return this;
    }

    public boolean isNeedRealTime() {
        return needRealTime;
    }

    public ColumnQueryFilter setNeedRealTime(boolean needRealTime) {
        this.needRealTime = needRealTime;
        return this;
    }
}

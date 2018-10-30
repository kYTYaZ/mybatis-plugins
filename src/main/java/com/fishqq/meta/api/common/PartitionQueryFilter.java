package com.fishqq.meta.api.common;

import com.alibaba.dt.dataphin.common.lang.criteria.OrderCriteria;
import com.alibaba.dt.dataphin.common.lang.criteria.PaginationCriteria;

/**
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/9/17
 */
public class PartitionQueryFilter {
    private Long tenantId;
    private OrderCriteria order;
    private PaginationCriteria paginationCriteria;
    private boolean needRealTime;
    private boolean needExt;
    private boolean needStat;

    public Long getTenantId() {
        return tenantId;
    }

    public PartitionQueryFilter setTenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public boolean isNeedRealTime() {
        return needRealTime;
    }

    public PartitionQueryFilter setNeedRealTime(boolean needRealTime) {
        this.needRealTime = needRealTime;
        return this;
    }

    public PaginationCriteria getPaginationCriteria() {
        return paginationCriteria;
    }

    public PartitionQueryFilter setPaginationCriteria(PaginationCriteria paginationCriteria) {
        this.paginationCriteria = paginationCriteria;
        return this;
    }

    public OrderCriteria getOrder() {
        return order;
    }

    public PartitionQueryFilter setOrder(OrderCriteria order) {
        this.order = order;
        return this;
    }

    public boolean isNeedExt() {
        return needExt;
    }

    public PartitionQueryFilter setNeedExt(boolean needExt) {
        this.needExt = needExt;
        return this;
    }

    public boolean isNeedStat() {
        return needStat;
    }

    public PartitionQueryFilter setNeedStat(boolean needStat) {
        this.needStat = needStat;
        return this;
    }
}

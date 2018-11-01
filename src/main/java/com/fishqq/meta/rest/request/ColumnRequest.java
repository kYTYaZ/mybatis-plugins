package com.alibaba.dt.dataphin.meta.client.rest.request;

import com.alibaba.dt.dataphin.meta.client.common.ColumnQueryFilter;

/**
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/9/20
 */
public class ColumnRequest extends BaseRequest {
    private ColumnQueryFilter filter;

    public ColumnQueryFilter getFilter() {
        return filter;
    }

    public void setFilter(ColumnQueryFilter filter) {
        this.filter = filter;
    }
}

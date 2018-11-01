package com.alibaba.dt.dataphin.meta.client.rest.request;

import com.alibaba.dt.dataphin.meta.client.common.TableQueryFilter;

/**
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/9/20
 */
public class TableRequest extends BaseRequest {
    private TableQueryFilter filter;

    public TableQueryFilter getFilter() {
        return filter;
    }

    public void setFilter(TableQueryFilter filter) {
        this.filter = filter;
    }
}

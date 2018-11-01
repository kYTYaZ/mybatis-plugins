package com.alibaba.dt.dataphin.meta.client.rest.request;

import java.util.List;

import com.alibaba.dt.dataphin.meta.client.common.PartitionQueryFilter;
import com.alibaba.dt.dataphin.meta.client.model.column.Range;

/**
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/9/20
 */
public class PartitionRequest extends BaseRequest {
    private PartitionQueryFilter filter;
    private List<Range<String>> ranges;

    public List<Range<String>> getRanges() {
        return ranges;
    }

    public void setRanges(List<Range<String>> ranges) {
        this.ranges = ranges;
    }

    public PartitionQueryFilter getFilter() {
        return filter;
    }

    public void setFilter(PartitionQueryFilter filter) {
        this.filter = filter;
    }
}

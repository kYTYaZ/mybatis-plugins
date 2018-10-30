package com.fishqq.meta.api.model.partition;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/9/13
 */
public class ExtData implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    public Date getCreateTime() {
        return createTime;
    }

    public ExtData setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public ExtData setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
        return this;
    }
}

package com.fishqq.meta.dos;

import java.util.List;

/**
 * 物理表过滤条件
 *
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/5/14
 */
public class FilterDO {
    private Long tenantId;

    private List<String> owners;

    private String keyword;

    private String orderType;

    private Boolean needExt;
    private Boolean needStat;
    private Boolean needBbox;
    private Boolean likeLogicProjectName;


    public List<String> getOwners() {
        return owners;
    }

    public void setOwners(List<String> owners) {
        this.owners = owners;
    }

    public Boolean getNeedExt() {
        return needExt;
    }

    public void setNeedExt(Boolean needExt) {
        this.needExt = needExt;
    }

    public Boolean getNeedBbox() {
        return needBbox;
    }

    public void setNeedBbox(Boolean needBbox) {
        this.needBbox = needBbox;
    }

    public Boolean getNeedStat() {
        return needStat;
    }

    public void setNeedStat(Boolean needStat) {
        this.needStat = needStat;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Boolean getLikeLogicProjectName() {
        return likeLogicProjectName;
    }

    public void setLikeLogicProjectName(Boolean likeLogicProjectName) {
        this.likeLogicProjectName = likeLogicProjectName;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    @Override
    public String toString() {
        return "FilterDO{" +
            "tenantId=" + tenantId +
            ", owners=" + owners +
            ", keyword='" + keyword + '\'' +
            ", orderType='" + orderType + '\'' +
            ", needExt=" + needExt +
            ", needBbox=" + needBbox +
            ", likeLogicProjectName=" + likeLogicProjectName +
            '}';
    }
}

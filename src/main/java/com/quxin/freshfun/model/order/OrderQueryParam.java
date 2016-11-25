package com.quxin.freshfun.model.order;

/**
 * Created by fanyanlin on 2016/11/24.
 * 查询条件入参
 */
public class OrderQueryParam {
    /**
     * 平台标识
     */
    private Long appId;
    /**
     * 订单号
     */
    private Long orderId;
    /**
     * 订单状态
     */
    private Integer orderStatus;
    /**
     * 下单开始时间
     */
    private Long beginTime;
    /**
     * 截止时间
     */
    private Long endTime;
    /**
     * 查询当前页码
     */
    private Integer page;
    /**
     * 总页码
     */
    private Integer pageSize;

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Long beginTime) {
        this.beginTime = beginTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}

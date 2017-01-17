package com.quxin.freshfun.model.order;

import java.util.List;

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
     * 公众号名
     */
    private String appName;
    /**
     * 用户编号
     */
    private String userId;
    /**
     * 公众号集合
     */
    private List<Long> appIdList;
    /**
     * 订单号
     */
    private String orderId;
    /**
     * 商品名
     */
    private String goodsName;
    /**
     * 商品标题
     */
    private String goodsTitle;
    /**
     * 商品编号集合
     */
    private List<Long> goodsIdList;
    /**
     * 买家昵称
     */
    private String nickName;
    /**
     * 收货人姓名
     */
    private String deliveryName;
    /**
     * 用户编号集合
     */
    private List<Long> userIdList;
    /**
     * 订单状态
     */
    private int orderStatus;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Long> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<Long> userIdList) {
        this.userIdList = userIdList;
    }

    public List<Long> getAppIdList() {
        return appIdList;
    }

    public void setAppIdList(List<Long> appIdList) {
        this.appIdList = appIdList;
    }

    public List<Long> getGoodsIdList() {
        return goodsIdList;
    }

    public void setGoodsIdList(List<Long> goodsIdList) {
        this.goodsIdList = goodsIdList;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getDeliveryName() {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
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

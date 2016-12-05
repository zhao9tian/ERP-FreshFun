package com.quxin.freshfun.model.outparam;

/**
 * Created by Ziming on 2016/11/30.
 * 公众号信息出参实体类
 */
public class AppInfoOutParam {
    /**
     * 公众号id
     */
    private Long appId;
    /**
     * 公众号名称
     */
    private String appName;
    /**
     * 注册时间
     */
    private Long created;
    /**
     * 订单数
     */
    private Integer orderNumber;
    /**
     * 前端显示金额
     */
    private String sumActualMoney;
    /**
     * 提现金额
     */
    private String withdrawMoney;

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getSumActualMoney() {
        return sumActualMoney;
    }

    public void setSumActualMoney(String sumActualMoney) {
        this.sumActualMoney = sumActualMoney;
    }

    public String getWithdrawMoney() {
        return withdrawMoney;
    }

    public void setWithdrawMoney(String withdrawMoney) {
        this.withdrawMoney = withdrawMoney;
    }
}

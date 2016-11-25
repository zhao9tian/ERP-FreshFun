package com.quxin.freshfun.model.order;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by fanyanlin on 2016/11/23.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderSaleInfo {
    /**
     * 订单数
     */
    private Integer orderNumber;
    /**
     * 累计销售额
     */
    private Integer sumActualPrice;
    /**
     * 前端显示金额
     */
    private String sumActualMoney;
    /**
     * 提现金额
     */
    private String withdrawMoney;

    public String getWithdrawMoney() {
        return withdrawMoney;
    }

    public void setWithdrawMoney(String withdrawMoney) {
        this.withdrawMoney = withdrawMoney;
    }

    public String getSumActualMoney() {
        return sumActualMoney;
    }

    public void setSumActualMoney(String sumActualMoney) {
        this.sumActualMoney = sumActualMoney;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getSumActualPrice() {
        return sumActualPrice;
    }

    public void setSumActualPrice(Integer sumActualPrice) {
        this.sumActualPrice = sumActualPrice;
    }

}

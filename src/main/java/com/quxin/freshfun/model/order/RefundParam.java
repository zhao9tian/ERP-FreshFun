package com.quxin.freshfun.model.order;

/**
 * Created by fanyanlin on 2016/12/15.
 * 退款参数
 */
public class RefundParam {
    /**
     * 退款编号
     */
    private Integer refundId;
    /**
     * 订单编号
     */
    private Long orderId;
    /**
     * 审核结果
     */
    private Integer action;
    /**
     * 退款金额
     */
    private String actualRefund;
    /**
     * 退款备注
     */
    private String refundCom;

    public Integer getRefundId() {
        return refundId;
    }

    public void setRefundId(Integer refundId) {
        this.refundId = refundId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public String getActualRefund() {
        return actualRefund;
    }

    public void setActualRefund(String actualRefund) {
        this.actualRefund = actualRefund;
    }

    public String getRefundCom() {
        return refundCom;
    }

    public void setRefundCom(String refundCom) {
        this.refundCom = refundCom;
    }
}

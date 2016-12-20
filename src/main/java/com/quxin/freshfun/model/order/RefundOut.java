package com.quxin.freshfun.model.order;

/**
 * Created by fanyanlin on 2016/11/11.
 * 退款出参
 */
public class RefundOut {
    /**
     * 退款编号
     */
    private Integer refundId;
    /**
     * 期望结果
     */
    private String result;
    /**
     * 退款原因
     */
    private String reason;
    /**
     * 退款金额
     */
    private String money;
    /**
     * 实际退款金额
     */
    private String actualRefundMoney;
    /**
     * 退款说明
     */
    private String refundContent;
    /**
     * 退款备注
     */
    private String remark;
    /**
     * 退款状态
     */
    private Integer state;

    public String getActualRefundMoney() {
        return actualRefundMoney;
    }

    public void setActualRefundMoney(String actualRefundMoney) {
        this.actualRefundMoney = actualRefundMoney;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getRefundContent() {
        return refundContent;
    }

    public void setRefundContent(String refundContent) {
        this.refundContent = refundContent;
    }

    public Integer getRefundId() {
        return refundId;
    }

    public void setRefundId(Integer refundId) {
        this.refundId = refundId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

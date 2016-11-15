package com.quxin.freshfun.model.order;

/**
 * Created by fanyanlin on 2016/11/11.
 * 退款出参
 */
public class RefundOut {
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
     * 退款说明
     */
    private String remark;


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

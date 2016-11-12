package com.quxin.freshfun.model.order;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 退款
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RefundPOJO {
    private Integer id;
    /**
     * 订单编号
     */
    private Long orderId;
    /**
     * 服务类型
     */
    private String serviceType;
    /**
     * 退款原因
     */
    private String returnReason;
    /**
     * 退款金额
     */
    private Integer returnMoney;
    /**
     * 退款说明
     */
    private String returnDes;

    private Long gmtCreate;

    private Long gmtModified;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason;
    }

    public Integer getReturnMoney() {
        return returnMoney;
    }

    public void setReturnMoney(Integer returnMoney) {
        this.returnMoney = returnMoney;
    }

    public String getReturnDes() {
        return returnDes;
    }

    public void setReturnDes(String returnDes) {
        this.returnDes = returnDes == null ? null : returnDes.trim();
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }
}
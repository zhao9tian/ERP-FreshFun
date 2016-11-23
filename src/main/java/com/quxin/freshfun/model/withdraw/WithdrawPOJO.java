package com.quxin.freshfun.model.withdraw;

/**
 * 提现实体类
 * Created by qucheng on 16/11/22.
 */
public class WithdrawPOJO {

    /**
     * 提现记录Id
     */
    private Long withdrawId;
    /**
     * appId
     */
    private Long appId;
    /**
     * 用户Id
     */
    private Long userId;
    /**
     * 账户类型 10 微信， 20 支付宝
     */
    private Integer accountType;
    /**
     * 账户账号
     */
    private String accountNum;
    /**
     * 提现金额
     */
    private Integer withdrawMoney;
    /**
     * 提现状态 0:提现中 10:提现成功 20:提现失败
     */
    private Integer state ;
    /**
     * 处理人
     */
    private Long handlerId ;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    private Long created ;
    /**
     * 编辑时间
     */
    private Long updated ;
    /**
     * 是否删除
     */
    private Integer isDeleted;

    public Long getWithdrawId() {
        return withdrawId;
    }

    public void setWithdrawId(Long withdrawId) {
        this.withdrawId = withdrawId;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public Integer getWithdrawMoney() {
        return withdrawMoney;
    }

    public void setWithdrawMoney(Integer withdrawMoney) {
        this.withdrawMoney = withdrawMoney;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getHandlerId() {
        return handlerId;
    }

    public void setHandlerId(Long handlerId) {
        this.handlerId = handlerId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getUpdated() {
        return updated;
    }

    public void setUpdated(Long updated) {
        this.updated = updated;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "WithdrawPOJO{" +
                "withdrawId=" + withdrawId +
                ", appId=" + appId +
                ", userId=" + userId +
                ", accountType=" + accountType +
                ", accountNum='" + accountNum + '\'' +
                ", withdrawMoney=" + withdrawMoney +
                ", state=" + state +
                ", handlerId=" + handlerId +
                ", remark='" + remark + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
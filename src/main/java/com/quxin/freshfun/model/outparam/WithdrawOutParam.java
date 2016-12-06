package com.quxin.freshfun.model.outparam;

/**
 * Created by Ziming on 2016/12/5.
 * 提现出参实体类
 */
public class WithdrawOutParam {
    private Long withdrawId;            //体现申请id
    private String appName;             //公众号名称
    private Long applyDate;             //申请时间
    private Long dealDate;              //处理时间，未处理的申请该参数为0
    private String withdrawMoney;       //提现金额
    private Integer accountType;        //提现渠道，10：微信，20：支付宝
    private String accountNum;          //提现帐号
    private Byte state;               //提现状态，0：提现中，10：已处理，20：已拒绝

    public Long getWithdrawId() {
        return withdrawId;
    }

    public void setWithdrawId(Long withdrawId) {
        this.withdrawId = withdrawId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Long getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Long applyDate) {
        this.applyDate = applyDate;
    }

    public Long getDealDate() {
        return dealDate;
    }

    public void setDealDate(Long dealDate) {
        this.dealDate = dealDate;
    }

    public String getWithdrawMoney() {
        return withdrawMoney;
    }

    public void setWithdrawMoney(String withdrawMoney) {
        this.withdrawMoney = withdrawMoney;
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

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }
}

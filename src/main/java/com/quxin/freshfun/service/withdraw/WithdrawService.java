package com.quxin.freshfun.service.withdraw;

import com.quxin.freshfun.model.withdraw.WithdrawPOJO;

import java.util.List;

/**
 * 提现、流水接口
 * Created by qucheng on 16/11/22.
 */
public interface WithdrawService {

    /**
     * 插入提现实体
     * @param withdrawPOJO 提现实体
     * @return 是否成功
     */
    Boolean addWithdraw(WithdrawPOJO withdrawPOJO);

    /**
     * 查询提现记录
     * @param appId 平台
     * @param start 分页开始
     * @param pageSize 页面大小
     * @return 提现对象
     */
    List<WithdrawPOJO> queryWithdrawListByAppId(Long appId , Integer start , Integer pageSize);

    /**
     * 查询可提现金额
     * @param appId 商户Id
     * @return 可提现金额
     */
    Integer queryAvailableMoney(Long appId);

    /**
     * 分页统计记录数
     * @param appId 商户Id
     * @return 记录数
     */
    Integer queryCountWithdrawByAppId(Long appId);

    /**
     * 查询累计入账金额
     * @param appId 商户Id
     * @return 累计入账金额
     */
    Integer queryTotalMoney(Long appId);

    /**
     * 查询未入账金额
     * @param appId 商户Id
     * @return 未入账金额
     */
    Integer queryUnrecordMoney(Long appId);
}

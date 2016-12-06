package com.quxin.freshfun.service.withdraw;

import com.quxin.freshfun.model.outparam.WithdrawOutParam;
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

    /**
     * 查询提现申请（分页）
     * @param curPage 当前页
     * @param pageSize 页面数据量
     * @param status 状态
     * @return 列表
     */
    List<WithdrawOutParam> queryWithdraws(Integer curPage, Integer pageSize, Integer status);

    /**
     * 处理提现申请
     * @param dealType 处理类型   0：通过，1：拒绝
     * @param withdrawId 提现申请id
     * @param crmUserId 处理人id
     * @return 处理结果
     */
    boolean dealWithdraw(Integer dealType,Long withdrawId,Long crmUserId,Long updated);

    /**
     * 查询提现申请（总数）
     * @param state 状态
     */
    Integer queryWithdrawCount(Integer state);
}

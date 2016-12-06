package com.quxin.freshfun.dao;

import com.quxin.freshfun.model.outparam.WithdrawOutParam;
import com.quxin.freshfun.model.withdraw.WithdrawPOJO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 提现、流水接口
 * Created by qucheng on 16/11/22.
 */
public interface WithdrawMapper {


    /**
     * 保存提现申请
     * @param withdrawPOJO 提现对象
     * @return 保存对象记录数
     */
    Integer insertWithdraw(WithdrawPOJO withdrawPOJO);


    /**
     * 分页查询商户提现记录
     * @param appId 商户Id
     * @param start 开始
     * @param pageSize 页面大小
     * @return 记录列表
     */
    List<WithdrawPOJO> selectWithdrawListByAppId(@Param("appId") Long appId, @Param("start")Integer start, @Param("pageSize")Integer pageSize);

    /**
     * 查询提现中的金额用于计算可提现金额
     * @param appId 商户Id
     * @return 提现中的总金额
     */
    Integer selectSumWithdrawingByAppId(Long appId);

    /**
     * 统计提现记录数
     * @param appId 商户Id
     * @return 记录条数
     */
    Integer selectCountWithdrawByAppId(Long appId);

    /**
     * 根据商户Id查询累计入账金额
     * @param appId 商户ID
     * @return 累计入账金额
     */
    Integer selectTotalMoneyByAppId(Long appId);

    /**
     * 根据商户Id查询未入账金额
     * @param appId 商户ID
     * @return 未入账金额
     */
    Integer selectUnrecordMoneyByAppId(Long appId);

    /**
     * 查询提现申请（分页）
     */
    List<WithdrawOutParam> selectWithdraws(Map<String,Object> map);

    /**
     * 处理提现申请
     */
    Integer dealWithdraw(Map<String ,Object> map);

    /**
     * 查询提现申请（总数）
     */
    Integer selectWithdrawCount(@Param(value="state")Integer state);

    /**
     * 根据id查询提现申请
     */
    WithdrawPOJO selectWithdrawById(Long id);
}

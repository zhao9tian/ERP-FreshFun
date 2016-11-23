package com.quxin.freshfun.dao;

import com.quxin.freshfun.model.flow.FlowPOJO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 流水Dao
 * Created by qucheng on 16/11/22.
 */
public interface FlowMapper {

    /**
     * 根据appId查询记录总数
     * @param appId 平台Id
     * @return 记录数
     */
    Integer selectCountByAppId(Long appId);

    /**
     * 根据appId查询流水列表
     * @param appId 平台Id
     * @return 流水记录
     */
    List<FlowPOJO> selectFlowListByAppId(@Param("appId") Long appId, @Param("start") Integer start, @Param("pageSize") Integer pageSize);

    /**
     * 根据订单Id查询流水详情
     * @param orderId 订单Id
     * @return 流水详情
     */
    FlowPOJO selectFlowByOrderId(Long orderId);

    /**
     * 根据AppId查询余额
     * @param appId 平台Id
     * @return 余额
     */
    Integer selectBalanceByAppId(Long appId);

    /**
     * 插入流水
     * @param flow 流水对象
     * @return 插入记录数
     */
    Integer insertFlow(FlowPOJO flow);
}

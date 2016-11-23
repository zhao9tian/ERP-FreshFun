package com.quxin.freshfun.service.flow;

import com.quxin.freshfun.model.flow.FlowPOJO;

import java.util.List;

/**
 * 流水service
 * Created by qucheng on 16/11/22.
 */
public interface FlowService {

    /**
     * 新增流水记录
     *
     * @param flow 流水记录
     * @return 是否成功
     */
    Boolean addFlow(FlowPOJO flow);

    /**
     * 根据appId查询流水
     * -- 分页
     * @param appId 平台Id
     *
     * @return 流水记录
     */
    List<FlowPOJO> queryFlowListByAppId(Long appId, Integer start, Integer pageSize);

    /**
     * 分页查询总数
     * @param appId 平台Id
     * @return 分页总数
     */
    Integer queryCountByAppId(Long appId);

    /**
     * 根据订单Id查询流水
     *
     * @param orderId 订单ID
     * @return 返回流水记录详情
     */
    FlowPOJO queryFlowByOrderId(Long orderId);

}

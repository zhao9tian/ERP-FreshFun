package com.quxin.freshfun.dao;

import com.quxin.freshfun.model.order.OrderDetailsPOJO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by gsix on 2016/10/20.
 */
public interface OrderDetailsMapper {
    /**
     * 查询所有订单
     * @return
     */
    List<OrderDetailsPOJO> selectBackstageOrders(@Param("currentPage") int currentPage, @Param("pageSize") int pageSize);

    /**
     * 查询所有订单数量
     * @return
     */
    Integer selectBackstageOrdersCount();

    /**
     * 根据订单状态查询订单列表
     * @return
     */
    List<OrderDetailsPOJO> selectOrderByOrderStatus(@Param("orderStatus") Integer orderStatus, @Param("currentPage") int currentPage, @Param("pageSize") int pageSize);

    /**
     * 根据订单状态查询订单数量
     * @param orderStatus
     * @return
     */
    Integer selectOrderByOrderStatusCount(Integer orderStatus);

    /**
     * 发货
     * @param map
     * @return
     */
    Integer deliverOrder(Map<String,Object> map);

    /**
     * 订单评论
     * @return
     */
    Integer orderRemark(Map<String,Object> map);
}

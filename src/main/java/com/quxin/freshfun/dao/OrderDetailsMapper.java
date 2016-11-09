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
     * 查询已完成订单
     * @return
     */
    List<OrderDetailsPOJO> selectFinishOrder(@Param("currentPage") Integer page,@Param("pageSize") Integer pageSize);

    /**
     * 查询已完成订单数量
     * @return
     */
    Integer selectFinishOrderCount();

    /**
     * 根据订单编号查询微信返回的订单编号
     * @param orderId
     * @return
     */
    OrderDetailsPOJO selectOrderTransactionIdInfo(Long orderId);

    /**
     * 根据父级订单编号查询订单编号
     * @param parentOrderId 父级订单编号
     * @return
     */
    List<Long> selectOrderIdByParentId(Long parentOrderId);

    /**
     * 根据订单编号查询支付金额
     * @param orderId
     * @return
     */
    Integer selectOrderPayPrice(Long orderId);

    /**
     * 修改订单退款状态
     * @param orderId
     * @param updateDate
     * @return
     */
    Integer updateOrderRefundStatus(@Param("orderId") Long orderId,@Param("updateDate") Long updateDate);

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

    /**
     * 删除订单
     * @param orderId
     * @return
     */
    Integer orderDel(Long orderId);
}

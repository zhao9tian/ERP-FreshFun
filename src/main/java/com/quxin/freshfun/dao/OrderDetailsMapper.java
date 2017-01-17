package com.quxin.freshfun.dao;

import com.quxin.freshfun.model.order.*;
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
    List<OrderDetailsPOJO> selectBackstageOrders(OrderQueryParam orderQueryParam);
    /**
     * 查询导出订单
     * @param orderQueryParam 订单条件
     * @return 订单集合
     */
    List<OrderDetailsPOJO> selectExportOrder(OrderQueryParam orderQueryParam);
    /**
     * 查询所有订单数量
     * @return
     */
    Integer selectBackstageOrdersCount(OrderQueryParam orderQueryParam);

    /**
     * 根据条件查询订单数量
     * @return
     */
    List<OrderNumPOJO> selectOrderNumCondition(OrderQueryParam orderQueryParam);

    /**
     * 查询订单数量
     * @return
     */
    List<OrderNumPOJO> selectOrderNum(@Param("appId") Long appId);

    /**
     * 根据appId批量查询下单数
     * @param ids appIds
     * @return 查询结果
     */
    List<OrderDetailsPOJO> selectCountByAppId(Long[] ids);

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
     * 根据appId查询销售信息
     * @param appId
     * @return
     */
    OrderSaleInfo selectSaleInfo(Long appId);

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

    /**
     * 查询订单退款之前状态
     * @return
     */
    String selectOrderRefundState(Long orderId);

    /**
     * 修改订单状体
     * @param map 参数集合
     * @return
     */
    Integer updateOrderState(Map<String,Object> map);

    /**
     * 根据订单Id查询订单详情
     * @param orderId 订单Id
     * @return 订单详情
     */
    OrderDetailsPOJO selectOrderDetailByOrderId(Long orderId);

    /**
     * 查询父级订单信息
     * @param orderId 订单编号
     * @return
     */
    OrdersPOJO selectParentPayPrice(Long orderId);
}

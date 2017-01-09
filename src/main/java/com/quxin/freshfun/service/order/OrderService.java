package com.quxin.freshfun.service.order;

import com.quxin.freshfun.model.order.*;
import com.quxin.freshfun.utils.BusinessException;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by gsix on 2016/10/20.
 */
public interface OrderService {
    /**
     * 查询所有订单
     * @return
     */
    Map<String,Object> selectBackstageOrders(OrderQueryParam orderParam) throws BusinessException;

    List<OrderDetailsPOJO> selectCountByAppId(Long[] ids);

    /**
     * 根据平台查询所有订单
     * @param orderQueryParam
     * @return
     */
    List<OrderDetailsPOJO> findOrdersByPlatform(OrderQueryParam orderQueryParam) throws BusinessException;

    /**
     * 查询所有订单数量
     * @return
     */
    Integer selectBackstageOrdersCount(OrderQueryParam orderQueryParam);

    /**
     * 根据订单状态查询订单列表
     * @return
     */
    List<OrderDetailsPOJO> selectOrderByOrderStatus(Integer orderStatus,int currentPage,int pageSize);

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
    List<OrderDetailsPOJO> findFinishOrder(Integer page, Integer pageSize);

    /**
     * 查询已完成订单数量
     * @return
     */
    Integer findFinishOrderCount();
    /**
     * 发货
     * @param order
     * @return
     */
    Integer deliverOrder(OrderDetailsPOJO order);
    /**
     * 订单评论
     * @return
     */
    Integer orderRemark(String orderId,String remark);

    /**
     * 删除订单
     * @param orderId
     * @return
     */
    Integer orderDel(Long orderId);

    /**
     * 订单退款
     * @param refundParam  参数
     * @return
     */
    String orderRefunds(RefundParam refundParam) throws BusinessException;

    /**
     * 驳回退款
     * @return
     */
    Integer rebutRefunds(RefundParam refundParam) throws BusinessException;

    /**
     * 后去订单数量
     * @return
     */
    Map<String,Object> getOrderNum(OrderQueryParam orderParam);

    /**
     * 根据订单编号查询退款详情
     * @return
     */
    RefundOut getRefundInfo(Long orderId) throws BusinessException;

    /**
     * 导出订单
     * @return
     */
    List<OrderDetailsPOJO> exportOrder(OrderQueryParam orderQueryParam);

    /**
     * 根据订单Id查询订单详情
     * create by qucheng
     * @param orderId 订单Id
     * @return 订单详情
     */
    OrderDetailsPOJO queryOrderDetailByOrderId(Long orderId);

    /**
     * 根据appId查询销售信息
     * @param appId
     * @return
     */
    OrderSaleInfo findSaleInfo(Long appId) throws BusinessException;
}

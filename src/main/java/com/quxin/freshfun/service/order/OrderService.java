package com.quxin.freshfun.service.order;

import com.quxin.freshfun.model.order.OrderDetailsPOJO;
import com.quxin.freshfun.model.order.RefundOut;
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
     * @param orderId  订单编号
     * @return
     */
    String orderRefunds(Long orderId) throws BusinessException;

    /**
     * 驳回退款
     * @return
     */
    Integer rebutRefunds(Long orderId) throws BusinessException;

    /**
     * 后去订单数量
     * @return
     */
    Map<String,Object> getOrderNum();

    /**
     * 根据订单编号查询退款详情
     * @return
     */
    RefundOut getRefundInfo(Long orderId) throws BusinessException;

    /**
     * 按时间区间查询订单
     * @return
     */
    List<OrderDetailsPOJO> getIntervalOrder(Integer orderState,Long startTime,Long endTime) throws BusinessException;

    /**
     * 按时间区间查询所有订单
     * @param startTime 开始时间
     * @param endTime  结束时间
     * @return
     */
    List<OrderDetailsPOJO> findAllIntervalOrder(Long startTime,Long endTime) throws BusinessException;

    /**
     * 按时间区间查询已完成订单
     * @param startTime
     * @param endTime
     * @return
     */
    List<OrderDetailsPOJO> findFinishIntervalOrder(Long startTime,Long endTime) throws BusinessException;

    /**
     * 根据订单Id查询订单详情
     * create by qucheng
     * @param orderId 订单Id
     * @return 订单详情
     */
    OrderDetailsPOJO queryOrderDetailByOrderId(Long orderId);


    /**
     * 根据appId查询下单数
     * @param appId 商城id
     * @return 下单数
     */
    Integer querySucOrderNum(String appId);

    /**
     * 根据appId查询下单金额
     * @param appId 商城id
     * @return 下单金额
     */
    Integer queryTotalRevenue(String appId);
}

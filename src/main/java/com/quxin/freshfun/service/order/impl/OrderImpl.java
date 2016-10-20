package com.quxin.freshfun.service.order.impl;

import com.google.common.collect.Maps;
import com.quxin.freshfun.dao.OrderDetailsMapper;
import com.quxin.freshfun.dao.UserBaseMapper;
import com.quxin.freshfun.model.order.OrderDetailsPOJO;
import com.quxin.freshfun.model.user.UserInfoOutParam;
import com.quxin.freshfun.service.order.OrderService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by gsix on 2016/10/20.
 */
@Service("orderService")
public class OrderImpl implements OrderService {
    @Autowired
    private OrderDetailsMapper orderDetailsMapper;
    @Autowired
    private UserBaseMapper userBaseMapper;

    /**
     * 查询所有订单信息
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public List<OrderDetailsPOJO> selectBackstageOrders(@Param("currentPage") int currentPage, @Param("pageSize") int pageSize) {
        List<OrderDetailsPOJO> orderDetails = orderDetailsMapper.selectBackstageOrders(currentPage, pageSize);
        for (OrderDetailsPOJO order : orderDetails) {
            UserInfoOutParam userInfo = userBaseMapper.selectUserInfoByUserId(order.getUserId());
            if(userInfo != null) {
                if(userInfo.getUserName() != null) {
                    order.setNickName(userInfo.getUserName());
                    order.setUserId(null);
                    order.setGoodsId(null);
                }
            }else{
                order.setNickName("");
            }
        }
        return orderDetails;
    }

    @Override
    public Integer selectBackstageOrdersCount() {
        return orderDetailsMapper.selectBackstageOrdersCount();
    }

    /**
     * 根据订单状态查询订单列表
     * @param orderStatus
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public List<OrderDetailsPOJO> selectOrderByOrderStatus(@Param("orderStatus") Integer orderStatus, @Param("currentPage") int currentPage, @Param("pageSize") int pageSize) {
        List<OrderDetailsPOJO> orderDetails = orderDetailsMapper.selectOrderByOrderStatus(orderStatus, currentPage, pageSize);
        for (OrderDetailsPOJO order: orderDetails) {
            UserInfoOutParam userInfo = userBaseMapper.selectUserInfoByUserId(order.getUserId());
            if(userInfo != null){
                if(userInfo.getUserName() != null) {
                    order.setNickName(userInfo.getUserName());
                    order.setUserId(null);
                }
            }else{
                order.setNickName("");
            }

        }
        return orderDetails;
    }

    @Override
    public Integer selectOrderByOrderStatusCount(Integer orderStatus) {
        return orderDetailsMapper.selectOrderByOrderStatusCount(orderStatus);
    }

    @Override
    public Integer orderRemark(String orderId,String remark) {
        if(StringUtils.isEmpty(orderId) || StringUtils.isEmpty(remark)){
            return null;
        }
        Map<String,Object> map = Maps.newHashMap();
        map.put("orderId",orderId);
        map.put("remark",remark);
        return orderDetailsMapper.orderRemark(map);
    }
}

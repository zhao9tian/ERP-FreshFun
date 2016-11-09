package com.quxin.freshfun.service.order.impl;

import com.google.common.collect.Maps;
import com.quxin.freshfun.dao.OrderDetailsMapper;
import com.quxin.freshfun.dao.UserBaseMapper;
import com.quxin.freshfun.model.order.OrderDetailsPOJO;
import com.quxin.freshfun.model.order.WxResult;
import com.quxin.freshfun.model.user.UserInfoOutParam;
import com.quxin.freshfun.service.order.OrderService;
import com.quxin.freshfun.utils.ConstantUtil;
import com.quxin.freshfun.utils.WxUtlis;
import com.quxin.freshfun.utils.WzConstantUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Map;

/**
 * Created by qingtian on 2016/10/20.
 */
@Service("orderService")
public class OrderImpl implements OrderService {
    @Autowired
    private OrderDetailsMapper orderDetailsMapper;
    @Autowired
    private UserBaseMapper userBaseMapper;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 查询所有订单信息
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public List<OrderDetailsPOJO> selectBackstageOrders(int currentPage,int pageSize) {
        List<OrderDetailsPOJO> orderDetails = orderDetailsMapper.selectBackstageOrders(currentPage, pageSize);
        orderDetails = getOrderDetails(orderDetails);
        return orderDetails;
    }

    /**
     * 获取用户信息
     * @return
     */
    private List<OrderDetailsPOJO> getOrderDetails(List<OrderDetailsPOJO> orderDetails) {
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
    public List<OrderDetailsPOJO> selectOrderByOrderStatus(Integer orderStatus,int currentPage,int pageSize) {
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

    /**
     * 已完成订单
     * @return
     */
    @Override
    public List<OrderDetailsPOJO> findFinishOrder(Integer page, Integer pageSize) {
        List<OrderDetailsPOJO> orderDetails = orderDetailsMapper.selectFinishOrder(page,pageSize);
        orderDetails = getOrderDetails(orderDetails);
        return orderDetails;
    }

    /**
     * 已完成订单数量
     * @return
     */
    @Override
    public Integer findFinishOrderCount() {
        return orderDetailsMapper.selectFinishOrderCount();
    }

    @Override
    public Integer deliverOrder(OrderDetailsPOJO order) {
        if(StringUtils.isEmpty(order.getActualMoney()) || StringUtils.isEmpty(order.getOrderId())) {
            return 0;
        }
        Long currentDate = System.currentTimeMillis()/1000;
        Map<String,Object> map = Maps.newHashMap();
        map.put("orderId",order.getOrderId());
        map.put("deliveryNum",order.getDeliveryNum());
        map.put("deliveryName",order.getDeliveryName());
        map.put("deliveryTime",currentDate);
        Double goodsCost = Double.parseDouble(order.getActualMoney())*100;
        map.put("goodsCost",goodsCost.intValue());
        return orderDetailsMapper.deliverOrder(map);
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

    @Override
    public Integer orderDel(Long orderId) {
        return orderDetailsMapper.orderDel(orderId);
    }

    /**
     * 订单退款
     * @param orderId
     * @return
     */
    @Override
    public String orderRefunds(Long orderId,String sign) {
        if(orderId == null || orderId == 0 || StringUtils.isEmpty(sign))
            return null;
        String refundResult = null;
        KeyStore keyStore = getKeyStore(sign);
        //发送请求
        switch (sign){
            case "wz":
                refundResult = orderDisposal(orderId,keyStore,WzConstantUtil.APP_ID,WzConstantUtil.PARTNER,WzConstantUtil.PARTNER_KEY);
                break;
            case "app":
                refundResult = orderDisposal(orderId,keyStore, ConstantUtil.APP_ID,ConstantUtil.PARTNER,ConstantUtil.PARTNER_KEY);
                break;
        }
        return refundResult;
    }

    /**
     * 获取微信退款的凭证
     * @param sign
     * @return
     */
    private KeyStore getKeyStore(String sign){
        InputStream instream = null;
        KeyStore keyStore = null;
        try {
            switch (sign){
                case "wz":
                    keyStore = KeyStore.getInstance("PKCS12");
                    instream = OrderImpl.class.getClassLoader().getResourceAsStream("wxconfig/apiclient_cert_wz.p12");
                    keyStore.load(instream, WzConstantUtil.PARTNER.toCharArray());
                    break;
                case "app":
                    keyStore = KeyStore.getInstance("PKCS12");
                    instream = OrderImpl.class.getClassLoader().getResourceAsStream("wxconfig/apiclient_cert_app.p12");
                    keyStore.load(instream, ConstantUtil.PARTNER.toCharArray());
                    break;
            }
        } catch (CertificateException e) {
            logger.error("订单退款获取KeyStore",e);
        } catch (NoSuchAlgorithmException e) {
            logger.error("订单退款获取KeyStore",e);
        } catch (KeyStoreException e1) {
            logger.error("订单退款获取KeyStore",e1);
        } catch (IOException e2) {
            logger.error("订单退款获取KeyStore",e2);
        } finally {
            try {
                instream.close();
            } catch (IOException e) {
                logger.error("订单退款获取关闭文件流",e);
            }
        }
        return keyStore;
    }

    /**
     * 处理订单,发送请求
     * @param orderId
     */
    private String orderDisposal(Long orderId,KeyStore keyStore,String appId,String partner,String partnerKey) {
        OrderDetailsPOJO orderDetails = orderDetailsMapper.selectOrderTransactionIdInfo(orderId);
        if(orderDetails == null)
            return null;
        if(StringUtils.isEmpty(orderDetails.getTransactionId()) || orderDetails.getActualPrice() == null)
            return null;

        //处理数据工具类
        RefundHandle refundHandle = new RefundHandle();
        String noncestr = WxUtlis.getNonceStr();

        refundHandle.setParameters("appid", appId);
        refundHandle.setParameters("mch_id", partner);
        refundHandle.setParameters("nonce_str",noncestr);
        refundHandle.setParameters("transaction_id",orderDetails.getTransactionId());
        refundHandle.setParameters("out_refund_no",orderDetails.getOrderId().toString());
        refundHandle.setParameters("total_fee",orderDetails.getActualPrice().toString());
        refundHandle.setParameters("refund_fee",orderDetails.getActualPrice().toString());
        refundHandle.setParameters("op_user_id",partner);
        //签名
        String sign = refundHandle.createMD5Sign(partnerKey);
        refundHandle.setParameters("sign",sign);
        /**
         * 请求返回结果
         */
        WxResult result = refundHandle.sendRefund(keyStore,partner);
        String refundResult = null;
        if("SUCCESS".equals(result.getReturn_code())){
            //修改订单退款状态
            if(modifyRefundOrderStatus(orderDetails.getId())){
                refundResult = "SUCCESS";
            } else {
                refundResult = "FAIL";
                logger.error("退款时修改订单状态失败");
            }
        }else{
            refundResult = "FAIL";
            logger.error("退款失败："+result.getReturn_msg());
        }
        return refundResult;
    }

    /**
     * 批量修改订单状态
     * @param orderId 子订单编号
     * @return 受影响行数
     */
    private Boolean modifyRefundOrderStatus(Long orderId) {
        Boolean bool = true;
        Long currentDate = WxUtlis.getCurrentDate();
        int modifyStatus = orderDetailsMapper.updateOrderRefundStatus(orderId,currentDate);
        if(modifyStatus <= 0)
            bool = false;
        return bool;
    }

}

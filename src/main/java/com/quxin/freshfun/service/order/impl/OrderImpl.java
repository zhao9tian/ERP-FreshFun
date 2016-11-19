package com.quxin.freshfun.service.order.impl;

import com.google.common.collect.Maps;
import com.quxin.freshfun.dao.OrderDetailsMapper;
import com.quxin.freshfun.dao.RefundMapper;
import com.quxin.freshfun.dao.UserBaseMapper;
import com.quxin.freshfun.model.order.OrderDetailsPOJO;
import com.quxin.freshfun.model.order.RefundOut;
import com.quxin.freshfun.model.order.RefundPOJO;
import com.quxin.freshfun.model.order.WxResult;
import com.quxin.freshfun.model.user.UserInfoOutParam;
import com.quxin.freshfun.service.address.AddressUtilService;
import com.quxin.freshfun.service.order.OrderService;
import com.quxin.freshfun.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.HashMap;
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
    @Autowired
    private RefundMapper refundMapper;
    @Autowired
    private AddressUtilService addressUtilService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    //待支付
    public final static Integer AWAIT_PAYMENT = 10;
    //待发货
    public final static Integer AWAIT_DELIVERY = 30;
    //待收货
    public final static Integer AWAIT_TAKE_GOODS = 50;
    //退款中
    public final static Integer REFUNDING = 40;
    //退款完成
    public final static Integer WAIT_DELIVERY = 20;
    //关闭
    public final static Integer CLOSE_ORDER = 15;

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
            //获取用户地址
            getAddress(order);
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

    /**
     * 获取用户地址
     * @param orderDetailsPOJO 订单实体
     */
    private void getAddress(OrderDetailsPOJO orderDetailsPOJO) {
        if(StringUtils.isEmpty(orderDetailsPOJO.getCity())){
            String city = addressUtilService.queryNameByCode(orderDetailsPOJO.getProvCode(), orderDetailsPOJO.getCityCode(), orderDetailsPOJO.getDistCode());
            orderDetailsPOJO.setCity(city);
        }
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
            //获取用户地址
            getAddress(order);
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

    @Override
    public Map<String, Object> getOrderNum() {
        Map<String,Object> map = new HashMap<>();
        //等待付款
        map.put("awaitPayment",orderDetailsMapper.selectOrderNum(AWAIT_PAYMENT));
        //待发货
        map.put("awaitDelivery",orderDetailsMapper.selectOrderNum(AWAIT_DELIVERY));
        //待收货
        map.put("takeGoods",orderDetailsMapper.selectOrderNum(AWAIT_TAKE_GOODS));
        //退款中
        map.put("refunding",orderDetailsMapper.selectOrderNum(REFUNDING));
        //退款完成
        map.put("refunded",orderDetailsMapper.selectOrderNum(WAIT_DELIVERY));
        //订单关闭
        map.put("closeOrder",orderDetailsMapper.selectOrderNum(CLOSE_ORDER));
        return map;
    }

    /**
     * 根据订单编号查询退款详情
     * @param orderId
     * @return
     */
    @Override
    public RefundOut getRefundInfo(Long orderId) throws BusinessException {
        if(orderId == null)
            throw new BusinessException("查询退款详情订单编号为null");
        RefundPOJO refundPOJO = refundMapper.selectRefundByOrderId(orderId);
        RefundOut refundOut = null;
        if(refundPOJO != null) {
            refundOut = new RefundOut();
            refundOut.setResult(refundPOJO.getServiceType());
            refundOut.setMoney(MoneyFormatUtils.getMoneyFromInteger(refundPOJO.getReturnMoney()));
            refundOut.setReason(refundPOJO.getReturnReason());
            refundOut.setRemark(refundPOJO.getReturnDes());
        }
        return refundOut;
    }

    /**
     * 按时间区间查询订单集合
     * @param orderState 订单状态
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return  订单集合
     */
    @Override
    public List<OrderDetailsPOJO> getIntervalOrder(Integer orderState, Long startTime, Long endTime) throws BusinessException {
        if(orderState == null || startTime == null || endTime == null)
            throw new BusinessException("按时间区间查询订单时间查询条件出错");
        Map<String,Object> map = new HashMap<>();
        map.put("orderStatus",orderState);
        map.put("beginTime",startTime);
        map.put("endTime",endTime);
        List<OrderDetailsPOJO> orderDetails = orderDetailsMapper.selectIntervalOrder(map);
        setAddress(orderDetails);
        return orderDetails;
    }

    /**
     * 查询订单时设置地址信息
     * @param orderDetails 订单数据
     */
    private void setAddress(List<OrderDetailsPOJO> orderDetails) {
        if(orderDetails != null){
            for (OrderDetailsPOJO order : orderDetails) {
                getAddress(order);
            }
        }
    }

    /**
     * 按时间区间查询所有订单
     * @param startTime 开始时间
     * @param endTime  结束时间
     * @return
     */
    @Override
    public List<OrderDetailsPOJO> findAllIntervalOrder(Long startTime, Long endTime) throws BusinessException {
        if(startTime == null || endTime == null)
            throw new BusinessException("按时间查询订单参数不能为null");
        Map<String,Object> map = new HashMap<>();
        map.put("beginTime",startTime);
        map.put("endTime",endTime);
        List<OrderDetailsPOJO> orderDetails = orderDetailsMapper.selectAllIntervalOrder(map);
        setAddress(orderDetails);
        return orderDetails;
    }

    /**
     * 按时间区间查询已完成订单
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     * @throws BusinessException
     */
    @Override
    public List<OrderDetailsPOJO> findFinishIntervalOrder(Long startTime, Long endTime) throws BusinessException {
        if(startTime == null || endTime == null)
            throw new BusinessException("按时间查询订单参数不能为null");
        Map<String,Object> map = new HashMap<>();
        map.put("beginTime",startTime);
        map.put("endTime",endTime);
        List<OrderDetailsPOJO> orderDetails = orderDetailsMapper.selectFinishIntervalOrder(map);
        setAddress(orderDetails);
        return orderDetails;
    }

    /**
     * 订单退款
     * @param orderId
     * @return
     */
    @Override
    public String orderRefunds(Long orderId) throws BusinessException {
        if(orderId == null)
            throw new BusinessException("退款编号不能为空");

        String refundResult = null;
        //查询订单信息
        OrderDetailsPOJO orderDetails = orderDetailsMapper.selectOrderTransactionIdInfo(orderId);
        if(orderDetails != null) {
            KeyStore keyStore = getKeyStore(orderDetails.getPaymentMethod());
            //发送请求
            switch (orderDetails.getPaymentMethod()) {
                case 1:
                    refundResult = orderDisposal(orderDetails, keyStore, WzConstantUtil.APP_ID, WzConstantUtil.PARTNER, WzConstantUtil.PARTNER_KEY);
                    break;
                case 2:
                    refundResult = orderDisposal(orderDetails, keyStore, ConstantUtil.APP_ID, ConstantUtil.PARTNER, ConstantUtil.PARTNER_KEY);
                    break;
            }
        }
        return refundResult;
    }

    /**
     * 驳回订单状态
     * @param orderId
     * @return
     */
    @Override
    public Integer rebutRefunds(Long orderId) throws BusinessException {
        if (orderId == null)
            throw new BusinessException("驳回退款订单号为null");
        String orderState = orderDetailsMapper.selectOrderRefundState(orderId);
        Integer state = 0;
        if(!StringUtils.isEmpty(orderState)){
            //修改订单状态
            Long currentDate = System.currentTimeMillis()/1000;
            Map<String,Object> map = new HashMap<>();
            map.put("orderStatus",orderState);
            map.put("updateDate",currentDate);
            map.put("orderId",orderId);
            state = orderDetailsMapper.updateOrderState(map);
            if(state <= 0)
                throw new BusinessException("驳回退款申请失败");
        }
        return state;
    }

    /**
     * 获取微信退款的凭证
     * @param sign
     * @return
     */
    private KeyStore getKeyStore(Integer sign){
        InputStream instream = null;
        KeyStore keyStore = null;
        try {
            switch (sign){
                case 1:
                    keyStore = KeyStore.getInstance("PKCS12");
                    instream = OrderImpl.class.getClassLoader().getResourceAsStream("wxconfig/apiclient_cert_wz.p12");
                    keyStore.load(instream, WzConstantUtil.PARTNER.toCharArray());
                    break;
                case 2:
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
     * @param orderDetails
     */
    private String orderDisposal(OrderDetailsPOJO orderDetails,KeyStore keyStore,String appId,String partner,String partnerKey) {
        if(StringUtils.isEmpty(orderDetails.getTransactionId()))
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

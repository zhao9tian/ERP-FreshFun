package com.quxin.freshfun.service.order.impl;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.quxin.freshfun.dao.GoodsMapper;
import com.quxin.freshfun.dao.OrderDetailsMapper;
import com.quxin.freshfun.dao.RefundMapper;
import com.quxin.freshfun.dao.UserBaseMapper;
import com.quxin.freshfun.model.goods.GoodsPOJO;
import com.quxin.freshfun.model.order.*;
import com.quxin.freshfun.model.outparam.SendWxMessage;
import com.quxin.freshfun.model.outparam.SendWxMessageContent;
import com.quxin.freshfun.model.outparam.WxPushMessageResult;
import com.quxin.freshfun.model.user.UserInfoOutParam;
import com.quxin.freshfun.service.address.AddressUtilService;
import com.quxin.freshfun.service.order.OrderService;
import com.quxin.freshfun.service.withdraw.WithdrawService;
import com.quxin.freshfun.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
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
    private GoodsMapper goodsMapper;
    @Autowired
    private RefundMapper refundMapper;
    @Autowired
    private AddressUtilService addressUtilService;
    @Autowired
    private WithdrawService withdrawService;

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
     * @return
     */
    @Override
    public Map<String,Object> selectBackstageOrders(OrderQueryParam orderParam) throws BusinessException {
        if(orderParam == null || orderParam.getPage() == null || orderParam.getPage() <= 0 || orderParam.getPageSize() == null)
            throw new BusinessException("查询订单参数为异常");
        Map<String,Object> map = new HashMap<>();
        orderParam.setPage((orderParam.getPage() - 1) * orderParam.getPageSize());
        //根据条件查询订单信息
        List<OrderDetailsPOJO> orderDetails = queryOrder(orderParam);
        //查询总页码
        Integer totalPage = orderDetailsMapper.selectBackstageOrdersCount(orderParam);
        //设置用户信息
        orderDetails = getOrderDetails(orderDetails);
        //设置金额格式
        orderDetails = MoneyFormatUtils.setBackstageMoney(orderDetails);
        //设置分页信息
        Integer total = getPageSize(totalPage,orderParam.getPageSize());

        map.put("totalPage",total);
        map.put("total",totalPage);
        map.put("page",orderParam.getPage());
        map.put("pageSize",orderParam.getPageSize());
        map.put("list",orderDetails == null ? new ArrayList<>() : orderDetails);
        return map;
    }

    /**
     * 根据条件查询订单信息
     * @param orderParam 条件参数
     * @return
     */
    private List<OrderDetailsPOJO> queryOrder(OrderQueryParam orderParam) {
        //根据商品信息查询
        if(!StringUtils.isEmpty(orderParam.getGoodsName()) && StringUtils.isEmpty(orderParam.getGoodsTitle()))
            orderParam.setGoodsIdList(goodsMapper.selectGoodsIdByGoodsName(orderParam));
        //根据公号名称
        if(!StringUtils.isEmpty(orderParam.getAppName()))
            orderParam.setAppIdList(userBaseMapper.selectAppIdByAppName(orderParam.getAppName()));
        //根据用户昵称
        if(!StringUtils.isEmpty(orderParam.getNickName()))
            orderParam.setUserIdList(userBaseMapper.selectUserIdByNickName(orderParam.getNickName()));

        return orderDetailsMapper.selectBackstageOrders(orderParam);
    }

    /**
     * 获取分页总页码
     * @param totalPage
     * @param pageSize
     * @return
     */
    private Integer getPageSize(Integer totalPage,Integer pageSize){
        Integer total = 0;
        if(totalPage % pageSize == 0){
            total = totalPage / pageSize;
        } else {
            total = totalPage / pageSize + 1;
        }
        return total;
    }

    /**
     * 根据平台查询所有订单
     * @param orderQueryParam 查询条件实体
     * @return
     */
    @Override
    public List<OrderDetailsPOJO> findOrdersByPlatform(OrderQueryParam orderQueryParam) throws BusinessException {
        if(orderQueryParam == null)
            throw new BusinessException("查询所有订单条件不能为空");
        List<OrderDetailsPOJO> orderDetails = orderDetailsMapper.selectBackstageOrders(orderQueryParam);
        //设置用户信息
        setOrderUserInfo(orderDetails);
        return orderDetails;
    }

    /**
     * 设置订单用户信息
     * @param orderDetails
     */
    private void setOrderUserInfo(List<OrderDetailsPOJO> orderDetails) {
        if(orderDetails != null){
            for (OrderDetailsPOJO order : orderDetails) {
                //设置用户地址信息
                getAddress(order);
                //设置用户隐私信息
                setUserPrivacy(order);
                //设置用户昵称
                setUserNickname(order);
            }
        }
    }

    /**
     * 设置用户隐私信息
     * @param order
     */
    private void setUserPrivacy(OrderDetailsPOJO order) {
        if(!StringUtils.isEmpty(order.getName()))
            order.setName(order.getName().replaceAll("([\\u4e00-\\u9fa5{1}])([\\u4e00-\\u9fa5]+)","$1**"));
        if(!StringUtils.isEmpty(order.getTel()))
            order.setTel(order.getTel().replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
        order.setAddress("******");
    }

    /**
     * 获取用户信息
     * @return
     */
    private List<OrderDetailsPOJO> getOrderDetails(List<OrderDetailsPOJO> orderDetails) {
        if(orderDetails == null)
            return null;
        for (OrderDetailsPOJO order : orderDetails) {
            //获取用户地址
            getAddress(order);
            //设置用户昵称
            setUserNickname(order);
            //设置订单来源
            setOrderSource(order);
        }
        return orderDetails;
    }

    /**
     * 设置订单来源
     * @param order
     */
    private void setOrderSource(OrderDetailsPOJO order) {
        if(order.getAppId() != null) {
            order.setOrderSource(userBaseMapper.selectAppNameByAppId(order.getAppId()));
            order.setAppId(null);
        }
    }

    /**
     * 设置用户昵称
     * @param order
     */
    private void setUserNickname(OrderDetailsPOJO order) {
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

    /**
     * 获取用户地址
     * @param orderDetailsPOJO 订单实体
     */
    private void getAddress(OrderDetailsPOJO orderDetailsPOJO) {
        if(StringUtils.isEmpty(orderDetailsPOJO.getCity())){
            String city = addressUtilService.queryNameByCode(orderDetailsPOJO.getProvCode(), orderDetailsPOJO.getCityCode(), orderDetailsPOJO.getDistCode());
            orderDetailsPOJO.setCity(city);
            orderDetailsPOJO.setProvCode(null);
            orderDetailsPOJO.setCityCode(null);
            orderDetailsPOJO.setDistCode(null);
        }else{
            orderDetailsPOJO.setProvCode(null);
            orderDetailsPOJO.setCityCode(null);
            orderDetailsPOJO.setDistCode(null);
        }
    }

    @Override
    public Integer selectBackstageOrdersCount(OrderQueryParam orderQueryParam) {
        return orderDetailsMapper.selectBackstageOrdersCount(orderQueryParam);
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
            //设置用户昵称
            setUserNickname(order);
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
        Integer result = orderDetailsMapper.deliverOrder(map);
        if(result == 1){
            //发送短信
            sendMessage(order);
            //推送消息
            sendWxOrderMessage(order);
        }
        return result;
    }

    private Boolean sendMessage(OrderDetailsPOJO order){
            //发货成功发送短信
            OrderDetailsPOJO orderDetailsPOJO = queryOrderDetailByOrderId(order.getOrderId());
            if (orderDetailsPOJO != null) {
                String phoneNum = orderDetailsPOJO.getTel();//手机号
                String shipperCode = order.getDeliveryName();
                String logisticCode = order.getDeliveryNum();
                String shipperName = ShipperNameUtils.getShipperNameByCode(shipperCode);
                Integer goodsId = orderDetailsPOJO.getGoodsId();
                GoodsPOJO goodsPOJO = goodsMapper.selectGoodsByGoodsId(Long.valueOf(goodsId));
                String title = "";
                if (goodsPOJO != null)
                    title = goodsPOJO.getTitle();
                StringBuilder content = new StringBuilder("您购买的");
                content.append(title).append("已经发货啦。\n发货单号是").append(shipperName).append(logisticCode);
                try {
                    MessageUtils.messageAtDelivery(phoneNum, content.toString());
                    return true ;
                } catch (IOException e) {
                    logger.error("订单Id为:" + order.getOrderId() + "短信发送失败");
                    return false ;
                }
            } else {
                logger.error("没有id为:" + order.getOrderId() + "订单");
            }
            return false;
    }

    /**
     * 下单成功发送消息
     * @return
     */
    public void sendWxOrderMessage(OrderDetailsPOJO order) {
        if(order == null)
            return;
        //查询订单信息
        OrderDetailsPOJO orderDetails = orderDetailsMapper.selectOrderDetailByOrderId(order.getOrderId());
        if(orderDetails != null){
            //获取accessToken
            WxAccessTokenInfo accessTokenInfo = WxUtlis.getAccessToken(WzConstantUtil.APP_ID, WzConstantUtil.APP_SECRET);
            order.setUserId(orderDetails.getUserId());
            sendOrderMessage(order,accessTokenInfo);
        }
    }

    /**
     * 发送订单信息
     * @param order
     * @param accessTokenInfo
     */
    private void sendOrderMessage(OrderDetailsPOJO order, WxAccessTokenInfo accessTokenInfo){
        if (order == null || accessTokenInfo == null)
            logger.error("往微信推订单信息，参数不能为空");
        StringBuilder msgUrl = new StringBuilder();
        msgUrl.append(WzConstantUtil.WX_MESSAGE);
        msgUrl.append(accessTokenInfo.getAccess_token());
        //获取用户openId
        String openId = userBaseMapper.selectUserInfoByUserId(order.getUserId()).getOpenId();
        SendWxMessage message = new SendWxMessage();
        message.setTouser(openId);
        message.setTemplate_id(WzConstantUtil.TEMPLATE_ID);
        message.setUrl(WzConstantUtil.ORDER_URL+order.getOrderId());
        message.setData(getOrderContent(order));
        Gson gson = new Gson();
        //发送请求
        String result = HttpClientUtils.jsonToPost(msgUrl.toString(), gson.toJson(message));
        WxPushMessageResult resultMessage = new WxPushMessageResult();
        resultMessage = WxUtlis.strToJson(result, resultMessage);
        if(!resultMessage.getErrmsg().equals("ok")){
            logger.error(new StringBuilder().append("用户编号：").append(order.getUserId()).append("推送消息失败").toString());
        }
    }

    /**
     * 获取订单内容
     * @param order
     */
    private Map<String,SendWxMessageContent> getOrderContent(OrderDetailsPOJO order) {
        Map<String,SendWxMessageContent> map = new HashMap<>();
        map.put("first",new SendWxMessageContent("您的订单已经发货啦。","#173177"));
        map.put("keyword1",new SendWxMessageContent(order.getOrderId(),"#173177"));
        //物流公司
        String shipperName = ShipperNameUtils.getShipperNameByCode(order.getDeliveryName());
        map.put("keyword2",new SendWxMessageContent(shipperName,"#173177"));
        map.put("keyword3",new SendWxMessageContent(order.getDeliveryNum(),"#173177"));
        map.put("remark",new SendWxMessageContent("如有问题您可直接在微信里留言，我们将在第一时间为您服务。","#173177"));
        return map;
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
    public Map<String, Object> getOrderNum(Long appId) {
        Map<String,Object> map = new HashMap<>();
        //等待付款
        map.put("awaitPayment",orderDetailsMapper.selectOrderNum(AWAIT_PAYMENT,appId));
        //待发货
        map.put("awaitDelivery",orderDetailsMapper.selectOrderNum(AWAIT_DELIVERY,appId));
        //待收货
        map.put("takeGoods",orderDetailsMapper.selectOrderNum(AWAIT_TAKE_GOODS,appId));
        //退款中
        map.put("refunding",orderDetailsMapper.selectOrderNum(REFUNDING,appId));
        //退款完成
        map.put("refunded",orderDetailsMapper.selectOrderNum(WAIT_DELIVERY,appId));
        //订单关闭
        map.put("closeOrder",orderDetailsMapper.selectOrderNum(CLOSE_ORDER,appId));
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
     * 导出订单
     * @param orderQueryParam
     * @return
     */
    @Override
    public List<OrderDetailsPOJO> exportOrder(OrderQueryParam orderQueryParam) {
        if(orderQueryParam == null)
            logger.error("订单导出参数为null");
        List<OrderDetailsPOJO> orderList = queryOrder(orderQueryParam);
        //设置金额格式
        MoneyFormatUtils.setBackstageMoney(orderList);
        exportOrderUserAddress(orderList);
        return orderList;
    }

    /**
     * 设置导出订单用户地址
     * @param orderList
     */
    private void exportOrderUserAddress(List<OrderDetailsPOJO> orderList) {
        if (orderList == null)
            return;
        for (OrderDetailsPOJO order : orderList) {
            getAddress(order);
        }
    }

    @Override
    public OrderDetailsPOJO queryOrderDetailByOrderId(Long orderId) {
        if(orderId == null){
            logger.error("orderId为空");
        }else{
            return orderDetailsMapper.selectOrderDetailByOrderId(orderId);
        }
        return null;
    }

    /**
     * 根据appId查询销售信息
     * @param appId
     * @return
     */
    @Override
    public OrderSaleInfo findSaleInfo(Long appId) throws BusinessException {
        if(StringUtils.isEmpty(appId))
            throw new BusinessException("根据appId查询订单销售信息不能为null");
        OrderSaleInfo orderSaleInfo = orderDetailsMapper.selectSaleInfo(appId);
        orderSaleInfo.setSumActualMoney(MoneyFormatUtils.getMoneyFromInteger(orderSaleInfo.getSumActualPrice()));
        orderSaleInfo.setSumActualPrice(null);
        Integer availableMoney = withdrawService.queryAvailableMoney(appId);
        orderSaleInfo.setWithdrawMoney(MoneyFormatUtils.getMoneyFromInteger(availableMoney));
        return orderSaleInfo;
    }

    /**
     * 订单退款
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
                    instream = OrderImpl.class.getClassLoader().getResourceAsStream("wxconfig/apiclient_cert.p12");
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
        if("SUCCESS".equals(result.getReturn_code()) && "SUCCESS".equals(result.getResult_code())){
            //修改订单退款状态
            if(modifyRefundOrderStatus(orderDetails.getId())){
                refundResult = "SUCCESS";
            } else {
                refundResult = "FAIL";
                logger.error("退款时修改订单状态失败");
            }
        }else{
            try {
                refundResult = new String(result.getErr_code_des().getBytes("ISO8859-1"),"utf-8");
                logger.error("退款失败："+ refundResult);
            } catch (UnsupportedEncodingException e) {
                logger.error("退款转码异常",e);
            }
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

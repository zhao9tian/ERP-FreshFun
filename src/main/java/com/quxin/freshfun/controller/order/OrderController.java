package com.quxin.freshfun.controller.order;

import com.quxin.freshfun.model.goods.GoodsOrderOut;
import com.quxin.freshfun.model.order.OrderDetailsPOJO;
import com.quxin.freshfun.service.order.OrderService;
import com.quxin.freshfun.utils.MoneyFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台订单管理
 * Created by gsix on 2016/10/20.
 */
@Controller
@RequestMapping("/")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 查询所有订单
     * @param page 当前页
     * @param pageSize 页面大小
     * @param orderStatus 订单状态
     * @return  返回请求结果
     */
    @RequestMapping("/selectBackstageOrders")
    @ResponseBody
    public Map<String, Object> selectBackstageOrders(Integer page, Integer pageSize, Integer orderStatus){
        Map<String, Object>  map = new HashMap<>();
        Map<String, Object>  resultMap = new HashMap<>();
        Map<String,Object> resultData = new HashMap<>();
        if(page == null || page <= 0 || pageSize == null || pageSize <= 0){
            map.put("code",1004);
            map.put("msg","传入当前页码不正确");
            resultMap.put("status",map);
            return resultMap;
        }
        if(orderStatus == null){
            map.put("code",1004);
            map.put("msg","传入订单状态不正确");
            resultMap.put("status",map);
            return resultMap;
        }
        int currentPage = (page - 1) * pageSize;
        List<OrderDetailsPOJO> order;
        int size;
        int total;
        switch (orderStatus){
            case 0:
                order = orderService.selectBackstageOrders(currentPage,pageSize);
                Integer count = orderService.selectBackstageOrdersCount();
                total = count;
                if(count % pageSize == 0){
                    size = count / pageSize;
                }else{
                    size = count / pageSize + 1;
                }
                break;
            default :
                order = orderService.selectOrderByOrderStatus(orderStatus,currentPage,pageSize);
                Integer number = orderService.selectOrderByOrderStatusCount(orderStatus);
                total = number;
                if(number % pageSize == 0){
                    size = number / pageSize;
                }else{
                    size = number /pageSize + 1;
                }
                break;
        }

        order = setBackstageMoney(order);
        map.put("code",1001);
        map.put("msg","请求成功");
        resultData.put("totalPage",size);
        resultData.put("total",total);
        resultData.put("page",page);
        resultData.put("pageSize",pageSize);
        resultData.put("list",order);
        resultMap.put("status",map);
        resultMap.put("data",resultData);
        return resultMap;
    }

    /**
     * 查询已完成订单
     * @return
     */
    @RequestMapping("/findFinishOrder")
    @ResponseBody
    public Map<String, Object> findFinishOrder(Integer page, Integer pageSize){
        Map<String, Object>  map = new HashMap<>();
        Map<String, Object>  resultMap = new HashMap<>();
        Map<String,Object> resultData = new HashMap<>();
        if(page == null || page == 0 || pageSize == null || pageSize <= 0){
            map.put("code",1004);
            map.put("msg","传入当前页码不正确");
            resultMap.put("status",map);
            return resultMap;
        }
        int currentPage = (page - 1) * pageSize;
        List<OrderDetailsPOJO> finishOrderList = orderService.findFinishOrder(currentPage, pageSize);
        Integer finishOrderCount = orderService.findFinishOrderCount();
        finishOrderList = setBackstageMoney(finishOrderList);
        int size = 1;
        if(finishOrderCount % pageSize == 0){
            size = finishOrderCount / pageSize;
        }else{
            size = finishOrderCount / pageSize + 1;
        }
        map.put("code",1001);
        map.put("msg","请求成功");
        resultData.put("totalPage",size);
        resultData.put("total",finishOrderCount);
        resultData.put("page",page);
        resultData.put("pageSize",pageSize);
        resultData.put("list",finishOrderList);
        resultMap.put("status",map);
        resultMap.put("data",resultData);
        return resultMap;
    }

    /**
     * 发货
     * @param order 物流信息
     * @return 请求是否成功
     */
    @RequestMapping(value="/deliverOrder",method={RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> deliverOrder(@RequestBody OrderDetailsPOJO order){
        Map<String, Object>  map = new HashMap<>();
        Map<String, Object>  resultMap = new HashMap<>();
        if(StringUtils.isEmpty(order.getOrderId())||StringUtils.isEmpty(order.getActualMoney())||StringUtils.isEmpty(order.getDeliveryNum())||StringUtils.isEmpty(order.getDeliveryName())){
            map.put("code",1004);
            map.put("msg","参数有误");
            resultMap.put("status",map);
            return resultMap;
        }else{
            int orderStatus = orderService.deliverOrder(order);
            if(orderStatus <= 0){
                map.put("code",1004);
                map.put("msg","发货失败");
                resultMap.put("status",map);
            }else{
                map.put("code",1001);
                map.put("msg","请求成功");
                resultMap.put("status",map);
            }
            resultMap.put("data",orderStatus);
        }
        return resultMap;
    }

    /**
     * 订单备注
     * @param orderId 订单Id
     * @param remark 备注内容
     * @return 请求结果
     */
    @RequestMapping("/orderRemark")
    @ResponseBody
    public Map<String,Object> orderRemark(String orderId,String remark) throws UnsupportedEncodingException {
        Map<String, Object>  map = new HashMap<>();
        Map<String, Object>  resultMap = new HashMap<>();

        if(StringUtils.isEmpty(orderId) || StringUtils.isEmpty(remark)){
            map.put("code",1004);
            map.put("msg","传入参数不正确");
            resultMap.put("status",map);
            return resultMap;
        }
        String utfRemark = new String(remark.getBytes("iso-8859-1") , "utf-8");
        Integer status = orderService.orderRemark(orderId, utfRemark);
        if(status == null || status <= 0){
            map.put("code",1004);
            map.put("msg","备注失败");
            resultMap.put("status",map);
            return resultMap;
        }else{
            map.put("code",1001);
            map.put("msg","请求成功");
            resultMap.put("status",map);
            resultMap.put("data",status);
        }
        return resultMap;
    }

    /**
     * 后台设置金额格式
     * @param order 订单内容
     * @return 订单列表
     */
    private List<OrderDetailsPOJO> setBackstageMoney(List<OrderDetailsPOJO> order){
        for (OrderDetailsPOJO o: order) {
            if(o.getGoodsCost() != null && o.getGoodsCost() != 0){
                o.setCostMoney(MoneyFormatUtils.getMoneyFromInteger(o.getGoodsCost()));
                o.setGoodsCost(null);
            }
            if(o.getPayPrice() != null && o.getPayPrice() != 0) {
                o.setPayMoney(MoneyFormatUtils.getMoneyFromInteger(o.getPayPrice()));
                o.setPayPrice(null);
            }
            GoodsOrderOut goods = o.getGoods();
            if(goods.getGoodsShopPrice() != null) {
                goods.setMarketMoney(MoneyFormatUtils.getMoneyFromInteger(goods.getGoodsShopPrice()));
                goods.setGoodsShopPrice(null);
            }
        }
        return order;
    }
}

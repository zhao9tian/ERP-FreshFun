package com.quxin.freshfun.controller.order;

import com.quxin.freshfun.model.goods.GoodsOrderOut;
import com.quxin.freshfun.model.goods.GoodsPOJO;
import com.quxin.freshfun.model.order.OrderDetailsPOJO;
import com.quxin.freshfun.service.order.OrderService;
import com.quxin.freshfun.utils.MoneyFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gsix on 2016/10/20.
 */
@Controller
@RequestMapping("/")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 所有订单
     * @param page
     * @return
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
        List<OrderDetailsPOJO> order = null;
        int size = 0;
        switch (orderStatus){
            case 0:
                order = orderService.selectBackstageOrders(currentPage,pageSize);
                Integer count = orderService.selectBackstageOrdersCount();
                if(count % pageSize == 0){
                    size = count / pageSize;
                }else{
                    size = count / pageSize + 1;
                }
                break;
            default :
                order = orderService.selectOrderByOrderStatus(orderStatus,currentPage,pageSize);
                Integer number = orderService.selectOrderByOrderStatusCount(orderStatus);
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
        resultData.put("page",page);
        resultData.put("pageSize",pageSize);
        resultData.put("list",order);
        resultMap.put("status",map);
        resultMap.put("data",resultData);
        return resultMap;
    }

    /**
     * 后台设置金额格式
     * @param order
     * @return
     */
    public List<OrderDetailsPOJO> setBackstageMoney(List<OrderDetailsPOJO> order){
        for (OrderDetailsPOJO o: order) {
            GoodsOrderOut goods = o.getGoods();
            if(goods.getGoodsShopPrice() != null) {
                goods.setMarketMoney(MoneyFormatUtils.getMoneyFromInteger(goods.getGoodsShopPrice()));
                goods.setGoodsShopPrice(null);
            }
        }
        return order;
    }
}

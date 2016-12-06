package com.quxin.freshfun.utils;

import com.quxin.freshfun.model.goods.GoodsOrderOut;
import com.quxin.freshfun.model.order.OrderDetailsPOJO;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 金额转换工具类
 * Created by qucheng on 2016/10/12.
 */
public class MoneyFormatUtils {

    /**
     * 将传入int型金额除以100以double保留两位小数的字符串形式传出
     * @param price 传入的int行金额
     * @return 返回保留两位小数的字符串
     */
    public static String getMoneyFromInteger(Integer price){
        DecimalFormat df = new DecimalFormat("#0.00");
        if(price != null){
            return df.format(((double) price)/100);
        }
        return null;
    }

    /**
     * 后台设置金额格式
     * @param order 订单内容
     * @return 订单列表
     */
    public static List<OrderDetailsPOJO> setBackstageMoney(List<OrderDetailsPOJO> order){
        if(order == null)
            return null;
        for (OrderDetailsPOJO o: order) {
            if(o.getActualPrice() != null){
                o.setActualMoney(MoneyFormatUtils.getMoneyFromInteger(o.getActualPrice()));
                o.setActualPrice(null);
            }
            if(o.getGoodsCost() != null){
                o.setCostMoney(MoneyFormatUtils.getMoneyFromInteger(o.getGoodsCost()));
                o.setGoodsCost(null);
            }
            if(o.getPayPrice() != null) {
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

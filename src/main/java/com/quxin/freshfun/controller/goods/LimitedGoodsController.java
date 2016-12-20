package com.quxin.freshfun.controller.goods;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.quxin.freshfun.model.goods.GoodsPOJO;
import com.quxin.freshfun.model.goods.LimitedGoodsPOJO;
import com.quxin.freshfun.service.goods.GoodsService;
import com.quxin.freshfun.service.goods.LimitedGoodsService;
import com.quxin.freshfun.utils.MoneyFormatUtils;
import com.quxin.freshfun.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 限量购
 * Created by qucheng on 16/12/14.
 */
@Controller
@RequestMapping("/limitedGoods")
public class LimitedGoodsController {

    @Autowired
    private LimitedGoodsService limitedGoodsService;

    @Autowired
    private GoodsService goodsService;

    /**
     * 查询限量购商品列表
     * 如果没有就返回一个空的数组
     *
     * @return 返回查询结果
     */
    @RequestMapping(value = "/getSortLimitGoods", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getSortLimitGoods() {
        List<LimitedGoodsPOJO> limitedGoodsPOJOs;
        List<Map<String, Object>> limitedGoodsOut = new ArrayList<>();
        limitedGoodsPOJOs = limitedGoodsService.queryLimitedGoodsList();
        Map<String, Object> data = Maps.newHashMap();
        if (limitedGoodsPOJOs != null && limitedGoodsPOJOs.size() > 0) {
            for (LimitedGoodsPOJO limitedGoodsPOJO : limitedGoodsPOJOs) {
                Map<String , Object> limitedGoods = Maps.newHashMap();
                limitedGoods.put("goodsId" , limitedGoodsPOJO.getLimitedGoodsId());
                limitedGoods.put("title",limitedGoodsPOJO.getGoodsTitle());
                limitedGoods.put("limitStock",limitedGoodsPOJO.getLimitedStock());
                limitedGoods.put("limitSaleNum",limitedGoodsPOJO.getLimitedStock() - limitedGoodsPOJO.getLimitedRealStock());
                limitedGoods.put("limitLeave", limitedGoodsPOJO.getLimitedRealStock());
                limitedGoods.put("shopPrice",MoneyFormatUtils.getMoneyFromInteger(limitedGoodsPOJO.getShopPrice()));
                limitedGoods.put("goodsStock", limitedGoodsPOJO.getGoodsLeaveStock());
                String limitedPrice = limitedGoodsPOJO.getLimitedPrice();
                Integer limitedPriceDB = null;
                if(limitedPrice != null){
                    Map<String , Object> discountPrice = JSON.parseObject(limitedPrice);
                    limitedPriceDB = (Integer) discountPrice.get("discountPrice");
                }
                limitedGoods.put("limitPrice" , MoneyFormatUtils.getMoneyFromInteger(limitedPriceDB));
                limitedGoodsOut.add(limitedGoods);
            }
        }
        data.put("sortlimitGoods", limitedGoodsOut);
        return ResultUtil.success(data);
    }

    /**
     * 根据商品Id查询商品基本信息
     *
     * @param goodsId 商品Id
     * @return 返回商品详情
     */
    @RequestMapping(value = "/getGoodsBaseByGoodsId", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getGoodsBaseByGoodsId(Long goodsId) {
        Map<String, Object> result;
        if (goodsId != null && goodsId != 0) {
            GoodsPOJO goods = goodsService.queryGoodsBaseByGoodsId(goodsId);
            if (goods != null) {
                if(goods.getIsOnSale() == 1){//上架
                    Map<String, Object> data = Maps.newHashMap();
                    data.put("title", goods.getTitle());
                    data.put("stock", goods.getStockNum()-goods.getSaleNum());//剩余库存 = 库存-销量
                    data.put("shopPrice", MoneyFormatUtils.getMoneyFromInteger(goods.getShopPrice()));
                    data.put("limitSaleNum", 0);//新增默认给0
                    result = ResultUtil.success(data);
                }else{//下架
                    result = ResultUtil.fail(1004 , "该商品已经下架");
                }
            } else {
                result = ResultUtil.fail(1004, "id为:"+goodsId+"的商品不存在");
            }
        } else {
            result = ResultUtil.fail(1004, "商品Id为空");
        }
        return result;
    }


    /**
     * 保存排序限量购商品
     *
     * @param limitedSortGoods 排序限量购商品
     * @return 返回保存结果
     */
    @RequestMapping(value = "/saveLimitGoodsSort", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveLimitGoodsSort(@RequestBody Map<String, List<Map<String, Object>>> limitedSortGoods) {
        Map<String, Object> result;
        if (limitedSortGoods != null && limitedSortGoods.size() > 0) {
            List<Map<String, Object>> limitedSort = limitedSortGoods.get("sortlimitGoods");
            if (limitedSort != null) {
                List<LimitedGoodsPOJO> limitedGoodsPOJOs = new ArrayList<>();
                for (Map<String, Object> limitedGoods : limitedSort) {
                    Long goodsId;
                    try {
                        goodsId = Long.valueOf((Integer) limitedGoods.get("goodsId"));
                        Integer limitStock = (Integer) limitedGoods.get("limitStock");
                        String limitPrice = (String) limitedGoods.get("limitPrice");

                        if (goodsId != null && limitStock != null && limitPrice != null) {
                            Map<String, Object> price = Maps.newHashMap();
                            Integer limitPriceDB = Math.round(Float.parseFloat(limitPrice) * 100);
                            price.put("discountPrice", limitPriceDB);
                            LimitedGoodsPOJO limitedGoodsPOJO = new LimitedGoodsPOJO();
                            limitedGoodsPOJO.setLimitedGoodsId(goodsId);
                            limitedGoodsPOJO.setLimitedStock(limitStock);
                            limitedGoodsPOJO.setLimitedPrice(JSON.toJSONString(price));
                            limitedGoodsPOJOs.add(limitedGoodsPOJO);
                        }
                    } catch (ClassCastException e) {
                        return ResultUtil.fail(1004, "参数类型出错:" + JSON.toJSONString(limitedGoods));
                    }
                }
                if (limitedGoodsService.addBatchLimitedGoods(limitedGoodsPOJOs)) {
                    result = ResultUtil.success();
                } else {
                    result = ResultUtil.fail(1004, "保存限量购排序失败,请检查您输入的商品信息");
                }
            } else {
                result = ResultUtil.fail(1004, "限量购排序对象为空");
            }
        } else {
            result = ResultUtil.fail(1004, "限量购排序对象为空");
        }
        return result;
    }

}

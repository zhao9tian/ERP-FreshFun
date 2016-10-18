package com.quxin.freshfun.controller.goods;

import com.quxin.freshfun.model.goods.GoodsPOJO;
import com.quxin.freshfun.model.goods.GoodsSortParam;
import com.quxin.freshfun.model.goods.GoodsSortOut;
import com.quxin.freshfun.service.goods.GoodsSortService;
import com.quxin.freshfun.utils.MoneyFormatUtils;
import com.quxin.freshfun.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品排序
 * Created by qucheng on 2016/10/12.
 */
@Controller
@RequestMapping("/goodsSort")
public class GoodsSortController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private GoodsSortService goodsSortService;

    /**
     * 查询排序商品
     * @return 返回查询结果
     */
    @RequestMapping(value = "/queryGoodsSortList" , method = RequestMethod.GET)
    @ResponseBody
    public Map<String , Object> queryGoodsSortList(){
        Map<String , Object> result = new HashMap<String , Object>();

        List<GoodsPOJO> goodsSorts = goodsSortService.querySortGoods();
        List<GoodsSortOut> goodsSortList = new ArrayList<>();
        if(goodsSorts != null && goodsSorts.size() > 0){
            for (GoodsPOJO  goods : goodsSorts) {
                GoodsSortOut goodsSort = new GoodsSortOut();
                //TODO
                goodsSort.setGoodsId(goods.getId());
                goodsSort.setGoodsName(goods.getGoodsName());
                goodsSort.setGoodsImg(goods.getGoodsImg());
                goodsSort.setGoodsPrice(MoneyFormatUtils.getMoneyFromInteger(goods.getGoodsShopPrice()));
                //TODO 先写个死数据
                goodsSort.setGoodsCost(MoneyFormatUtils.getMoneyFromInteger(10000000));
                goodsSortList.add(goodsSort);
            }
            result = ResultUtil.success(goodsSortList);
        }else{
            result = ResultUtil.fail(1004 , "没有排序过的商品");
        }
        return result;
    }

    /**
     * 根据商品Id查询商品主要信息
     * @param goodsId 商品ID
     * @return 返回查询结果
     */
    @RequestMapping(value = "/queryGoodsById" , method = RequestMethod.GET)
    @ResponseBody
    public Map<String , Object> queryGoodsById(Integer goodsId){
        Map<String , Object> result = new HashMap<String , Object>();
        if(goodsId == null){
            result.put("code",1004);
            result.put("msg","商品Id为空");
        }else{
            GoodsPOJO goods = goodsSortService.querySortGoodsById(goodsId);
            //对象不存在
            if(goods != null){
                //TODO 给出参对象赋值 成本价木有
                GoodsSortOut goodsSortOut = new GoodsSortOut();
                goodsSortOut.setGoodsId(goodsId);
                goodsSortOut.setGoodsName(goods.getGoodsName());
                goodsSortOut.setGoodsImg(goods.getGoodsImg());
                goodsSortOut.setGoodsPrice(MoneyFormatUtils.getMoneyFromInteger(goods.getGoodsShopPrice()));
                goodsSortOut.setGoodsCost(MoneyFormatUtils.getMoneyFromInteger(5222));
                result = ResultUtil.success(goodsSortOut);
            }else{
                result = ResultUtil.fail(1004 , "商品已经下架");
            }
        }
        return result;
    }


    @RequestMapping(value = "/saveAllGoodsSort" , method = RequestMethod.POST)
    @ResponseBody
    public Map<String , Object> saveAllGoodsSort(@RequestBody List<Integer> allSort){
        Map<String , Object> result =  new HashMap<String , Object>();
        //将页面传过来的排列顺序和id传入service
        if(goodsSortService.addAllGoodsSort(allSort)){
            result = ResultUtil.success("success");
        }else{
            result = ResultUtil.fail(1004 , "保存排序信息失败");
            logger.error("保存排序信息失败");
        }
        return result;
    }
}

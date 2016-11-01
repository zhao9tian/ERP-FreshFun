package com.quxin.freshfun.controller.goods;

import com.quxin.freshfun.model.goods.GoodsPOJO;
import com.quxin.freshfun.model.goods.GoodsSelectionPOJO;
import com.quxin.freshfun.service.goods.GoodsService;
import com.quxin.freshfun.service.goods.GoodsSortService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 商品controller
 * Created by qucheng on 2016/10/15.
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsSortService goodsSortService;

    /**
     * 添加商品信息
     *
     * @param goodsInfo 商品信息
     * @return 返回请求结果
     */
    @RequestMapping(value = "/addGoods", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addGoods(@RequestBody Map<String, Object> goodsInfo) {
        //TODO 检验入参

        //保存商品信息
        Map<String, Object> map = (Map<String, Object>) goodsInfo.get("CategoryInfo");
        System.out.println(map.get("first"));
        GoodsPOJO goodsPOJO = new GoodsPOJO();
        Boolean isGoodsSuc = goodsService.addGoods(goodsPOJO);
        return null;
    }

    //TODO 商品列表分页  搜索
    @RequestMapping(value = "/queryGoodsList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryGoodsList() {


        return null;
    }

    //TODO 商品编辑
    @RequestMapping(value = "/queryGoodsList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> editGoodsList() {


        return null;
    }



}

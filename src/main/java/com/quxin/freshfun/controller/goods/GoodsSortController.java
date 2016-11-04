package com.quxin.freshfun.controller.goods;

import com.quxin.freshfun.model.goods.*;
import com.quxin.freshfun.service.goods.GoodsSortService;
import com.quxin.freshfun.utils.MoneyFormatUtils;
import com.quxin.freshfun.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
     *
     * @return 返回查询结果
     */
    @RequestMapping(value = "/queryGoodsSortList", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryGoodsSortList() {
        Map<String, Object> result;
        List<GoodsPOJO> goodsSorts = goodsSortService.querySortGoods();
        List<GoodsSortOut> goodsSortList = new ArrayList<>();
        if (goodsSorts != null && goodsSorts.size() > 0) {
            for (GoodsPOJO goods : goodsSorts) {
                GoodsSortOut goodsSort = new GoodsSortOut();
                goodsSort.setGoodsId(goods.getGoodsId());
                goodsSort.setGoodsName(goods.getTitle());
                goodsSort.setGoodsImg(goods.getGoodsImg());
                goodsSort.setGoodsPrice(MoneyFormatUtils.getMoneyFromInteger(goods.getShopPrice()));
                goodsSort.setGoodsCost(MoneyFormatUtils.getMoneyFromInteger(goods.getGoodsCost()));
                goodsSortList.add(goodsSort);
            }
            result = ResultUtil.success(goodsSortList);
        } else {
            result = ResultUtil.fail(1004, "没有已排序的商品");
        }
        return result;
    }

    /**
     * 根据商品Id查询商品主要信息
     *
     * @param goodsId 商品ID
     * @return 返回查询结果
     */
    @RequestMapping(value = "/queryGoodsById", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryGoodsById(Long goodsId) {
        Map<String, Object> result;
        if (goodsId == null) {
            result = ResultUtil.fail(1004, "商品Id不能为空");
            logger.error("商品Id不能为空");
        } else {
            GoodsPOJO goods = goodsSortService.querySortGoodsById(goodsId);
            //对象不存在
            if (goods != null) {
                GoodsSortOut goodsSortOut = new GoodsSortOut();
                goodsSortOut.setGoodsId(goodsId);
                goodsSortOut.setGoodsName(goods.getTitle());
                goodsSortOut.setGoodsImg(goods.getGoodsImg());
                goodsSortOut.setGoodsPrice(MoneyFormatUtils.getMoneyFromInteger(goods.getShopPrice()));
                goodsSortOut.setGoodsCost(MoneyFormatUtils.getMoneyFromInteger(goods.getGoodsCost()));
                result = ResultUtil.success(goodsSortOut);
            } else {
                result = ResultUtil.fail(1004, "商品不存在或者已下架");
                logger.error("商品不存在或者已下架");
            }
        }
        return result;
    }

    /**
     * 接收排序参数 泛型为Integer
     *
     * @param allSort 排序参数
     * @return 返回请求结果
     */
    @RequestMapping(value = "/saveAllGoodsSort", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveAllGoodsSort(@RequestBody Map<String, List<Integer>> allSort) {
        Map<String, Object> result;
        List<Integer> sort = allSort.get("allSort");
        List<Long> goodsSort = new ArrayList<>();
        if (sort != null && sort.size() > 0) {
            if (allSort.size() > 20) {
                logger.error("排序对象总数大于20");
                result = ResultUtil.fail(1004, "排序对象总数大于20");
            } else {
                //将页面传过来的排列顺序和id传入service
                for(Integer goodsId : sort){
                    try{
                        Long goodsIdLong = Long.valueOf(goodsId);
                        goodsSort.add(goodsIdLong);
                    }catch (ClassCastException e){
                        logger.error("商品id类型不为Long" , e);
                        return ResultUtil.fail(1004, "传入的Id类型不正确");
                    }
                }
                if (goodsSortService.addAllGoodsSort(goodsSort)) {
                    result = ResultUtil.success("success");
                } else {
                    result = ResultUtil.fail(1004, "保存排序信息失败");
                    logger.error("保存排序信息失败");
                }
            }
        } else {
            logger.error("传入的排序对象不能为空");
            result = ResultUtil.fail(1004, "传入的排序对象不能为空");
        }
        return result;
    }

    /**
     * 查询所有精选商品
     *
     * @return 返回请求结果
     */
    @RequestMapping(value = "/querySelection", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> querySelection() {
        Map<String, Object> result;
        List<GoodsSelectionPOJO> goodsSelectionPOJOs = goodsSortService.querySelectionSort();
        List<GoodsSelectionOut> goodsSelectionOuts = new ArrayList<>();
        if (goodsSelectionPOJOs != null && goodsSelectionPOJOs.size() > 0) {
            for (GoodsSelectionPOJO goodsSelectionPOJO : goodsSelectionPOJOs) {
                GoodsSelectionOut goodsSelectionOut = new GoodsSelectionOut();
                goodsSelectionOut.setSelectionImg(goodsSelectionPOJO.getSelectionImg());
                goodsSelectionOut.setGoodsId(goodsSelectionPOJO.getGoodsPOJO().getGoodsId());
                goodsSelectionOut.setGoodsTitle(goodsSelectionPOJO.getGoodsPOJO().getTitle());
                goodsSelectionOuts.add(goodsSelectionOut);
            }
        }
        Map<String, Object> data = new HashMap<>();
        data.put("selectionSort", goodsSelectionOuts);
        result = ResultUtil.success(data);
        return result;
    }

    /**
     * 保存排序的精选商品
     *
     * @param selectionSortParam 精选商品排序
     * @return 请求结果
     */
    @RequestMapping(value = "/addSelection", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addSelection(@RequestBody Map<String, List<Map<String, Object>>> selectionSortParam) {
        Map<String, Object> result = new HashMap<>();
        if (selectionSortParam != null && selectionSortParam.size() > 0) {
            List<Map<String, Object>> selectionSort = selectionSortParam.get("selectionSort");
            if (selectionSort != null && selectionSort.size() > 0) {
                List<Map<String, Object>> selectionSortPOJO = new ArrayList<>();
                for (Map<String, Object> selection : selectionSort) {
                    Long goodsId = null;
                    try{
                        goodsId = Long.valueOf((Integer) selection.get("goodsId"));
                    }catch (ClassCastException e){
                        logger.error("商品Id类型不正确" ,e);
                        result = ResultUtil.fail(1004, "商品Id类型不正确");
                    }
                    String selectionImg = (String) selection.get("selectionImg");
                    if (goodsId != null && goodsId != 0 && selectionImg != null && !"".equals(selectionImg)) {
                        Map<String, Object> selectionPOJO = new HashMap<>();
                        selectionPOJO.put("goodsId", goodsId);//将商品Id转为Long
                        selectionPOJO.put("img", selectionImg);
                        selectionSortPOJO.add(selectionPOJO);
                    }else{
                        logger.error("精选入参为出错");
                        result = ResultUtil.fail(1004, "精选入参为出错");
                    }
                }
                if (goodsSortService.addSelectionSort(selectionSortPOJO)) {
                    Map<String, Object> status = new HashMap<>();
                    status.put("code", 1001);
                    status.put("msg", "请求成功");
                    result.put("status", status);
                } else {
                    logger.error("保存精选排序失败");
                    result = ResultUtil.fail(1004, "保存精选排序失败,请检查您输入的商品信息");
                }
            } else {
                logger.error("精选排序对象为空");
                result = ResultUtil.fail(1004, "精选排序对象为空");
            }
        } else {
            logger.error("精选排序对象为空");
            result = ResultUtil.fail(1004, "精选排序对象为空");
        }
        return result;
    }

    /**
     * 保存bannerSort排序
     * @return 请求是否成功
     */
    @RequestMapping(value = "/addBannerSort", method = RequestMethod.POST)
    @ResponseBody
    public Map<String , Object> saveBannerSort(@RequestBody  Map<String , List<Map<String, Object>>> bannerSortParam){
        Map<String, Object> result = new HashMap<>();
        if (bannerSortParam != null && bannerSortParam.size() > 0) {
            List<Map<String, Object>> bannerSort = bannerSortParam.get("bannerSort");
            if (bannerSort != null && bannerSort.size() > 0) {
                List<Map<String, Object>> bannerSortPOJO = new ArrayList<>();
                for (Map<String, Object> banner : bannerSort) {
                    Long themeId;
                    try{
                        themeId = Long.valueOf((Integer) banner.get("themeId"));
                    }catch (ClassCastException e){
                        logger.error("商品Id类型不正确" ,e);
                        return ResultUtil.fail(1004, "商品Id类型不正确");
                    }
                    String img = (String) banner.get("img");
                    String url = (String) banner.get("url");
                    if (themeId != null && themeId != 0 && img != null && !"".equals(img)) {
                        Map<String, Object> bannerPOJO = new HashMap<>();
                        bannerPOJO.put("themeId", themeId);//将专题Id转为Long
                        bannerPOJO.put("img", img);
                        bannerPOJO.put("url", url);
                        bannerSortPOJO.add(bannerPOJO);
                    }else{
                        logger.error("专题入参出错");
                        result = ResultUtil.fail(1004, "专题入参出错");
                    }
                }
                if (goodsSortService.addBannerSort(bannerSortPOJO)) {
                    Map<String, Object> status = new HashMap<>();
                    status.put("code", 1001);
                    status.put("msg", "请求成功");
                    result.put("status", status);
                } else {
                    logger.error("保存专题排序失败");
                    result = ResultUtil.fail(1004, "保存banner排序失败,请检查您输入的专题信息");
                }
            } else {
                logger.error("专题排序对象为空");
                result = ResultUtil.fail(1004, "专题排序对象为空");
            }
        } else {
            logger.error("专题排序对象为空");
            result = ResultUtil.fail(1004, "专题排序对象为空");
        }
        return result;
    }

    /**
     * 查询所有精选商品
     *
     * @return 返回请求结果
     */
    @RequestMapping(value = "/queryBannerSort", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryBannerSort() {
        Map<String, Object> result;
        List<BannerPOJO> bannerPOJOs = goodsSortService.queryBannerSort();
        List<BannerOut> bannerOuts = new ArrayList<>();
        if (bannerPOJOs != null && bannerPOJOs.size() > 0) {
            for (BannerPOJO bannerPOJO : bannerPOJOs) {
                BannerOut bannerOut = new BannerOut();
                bannerOut.setImg(bannerPOJO.getBannerImg());
                bannerOut.setThemeId(bannerPOJO.getThemePOJO().getThemeId());
                bannerOut.setThemeName(bannerPOJO.getThemePOJO().getThemeName());
                bannerOut.setUrl(bannerPOJO.getUrl());
                bannerOuts.add(bannerOut);
            }
        }
        Map<String, Object> data = new HashMap<>();
        data.put("bannerSort", bannerOuts);
        result = ResultUtil.success(data);
        return result;
    }
}

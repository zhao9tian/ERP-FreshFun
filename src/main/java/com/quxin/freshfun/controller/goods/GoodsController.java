package com.quxin.freshfun.controller.goods;

import com.alibaba.fastjson.JSON;
import com.quxin.freshfun.model.goods.GoodsBaseOut;
import com.quxin.freshfun.model.goods.GoodsPOJO;
import com.quxin.freshfun.model.goods.GoodsStandardKV;
import com.quxin.freshfun.model.goods.GoodsStandardPOJO;
import com.quxin.freshfun.service.goods.GoodsService;
import com.quxin.freshfun.service.goods.GoodsSortService;
import com.quxin.freshfun.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
     * 保存商品信息 -- 编辑
     *
     * @param goodsInfo 商品信息
     * @return 返回请求结果
     */
    @RequestMapping(value = "/addGoods", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addGoods(@RequestBody Map<String, Object> goodsInfo) {
        Map<String, Object> result = new HashMap<>();
        GoodsPOJO goodsPOJO = validateGoodsInfo(goodsInfo);
        if (goodsPOJO != null) {
            goodsPOJO.setAppId(0L);//默认
            goodsPOJO.setShopId(0L);//默认
            if(goodsService.isExistTitle(goodsPOJO.getTitle())){
                if (goodsService.addGoods(goodsPOJO)) {
                    Map<String, Object> status = new HashMap<>();
                    status.put("code", 1001);
                    status.put("msg", "保存成功");
                    result.put("status", status);
                } else {
                    result = ResultUtil.fail(1004, "保存商品信息失败");
                }
            }else{
                result = ResultUtil.fail(1004, "商品名称已存在出错");
            }
        } else {
            logger.error("商品信息入参出错");
            result = ResultUtil.fail(1004, "商品入参出错");
        }
        return result;
    }


    /**
     * 返回列表信息
     *
     * @param queryCondition 查询条件
     * @return 返回查询结果
     */
    @RequestMapping(value = "/queryGoodsList", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryGoodsList(@RequestBody Map<String, Object> queryCondition) {
        Map<String, Object> result;
        List<GoodsPOJO> goods = goodsService.queryAllGoods(queryCondition);
        List<GoodsBaseOut> goodsBases = new ArrayList<>();
        if (goods != null) {
            Map<String, Integer> pageInfo = goodsService.queryPagingInfo(queryCondition);
            for (GoodsPOJO goodsPOJO : goods) {
                GoodsBaseOut goodsBase = new GoodsBaseOut();
                goodsBase.setGoodsId(goodsPOJO.getGoodsId());
                goodsBase.setImg(goodsPOJO.getGoodsImg());
                goodsBase.setShopPrice(goodsPOJO.getShopPrice());
                goodsBase.setOriginPrice(goodsPOJO.getOriginPrice());
                goodsBase.setTitle(goodsPOJO.getTitle());
                goodsBase.setCreateTime(goodsPOJO.getCreated());
                goodsBase.setIsOnSale(goodsPOJO.getIsOnSale());
                goodsBase.setSaleNum(goodsPOJO.getSaleNum());
                goodsBase.setStoreNum(goodsPOJO.getStockNum());
                goodsBases.add(goodsBase);
            }
            //返回结果
            Map<String, Object> data = new HashMap<>();
            data.put("list", goodsBases);
            data.put("totalPage", pageInfo.get("totalPage"));
            data.put("total", pageInfo.get("total"));
            result = ResultUtil.success(data);
        } else {
            result = ResultUtil.fail(1004, "查询条件有误");
        }
        return result;
    }


    /**
     * 根据商品Id查询商品详情
     *
     * @param goodsId 商品Id
     * @return 返回商品详情
     */
    @RequestMapping(value = "/queryGoodsByGoodsId", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryGoodsById(Long goodsId) {
        Map<String, Object> result;
        if (goodsId != null && goodsId != 0) {
            GoodsPOJO goods = goodsService.queryGoodsByGoodsId(goodsId);
            if (goods != null) {
                Map<String, Object> data = goodsToMap(goods);
                result = ResultUtil.success(data);
            } else {
                result = ResultUtil.fail(1004, "查询商品详情失败");
            }
        } else {
            result = ResultUtil.fail(1004, "入参有误");
        }
        return result;
    }


    /**
     * 商品上下架
     *
     * @param goodsId 商品Id
     * @param status  上下架
     * @return 请求是否成功
     */
    @RequestMapping(value = "/changeStatus", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> changeStatus(Long goodsId, Integer status) {
        Map<String, Object> result = new HashMap<>();
        if (goodsId != null && goodsId != 0 && status != null && status != 0) {
            if (goodsService.changeStatus(goodsId, 10, status)) {
                Map<String, Object> statusReturn = new HashMap<>();
                statusReturn.put("code", 1001);
                statusReturn.put("msg", "请求成功");
                result.put("status", statusReturn);
            } else {
                result = ResultUtil.fail(1004, "修改商品上下架失败");
            }
        } else {
            result = ResultUtil.fail(1004, "入参有误");
        }
        return result;
    }

    /**
     * 删除商品
     *
     * @param goodsId 商品Id
     * @return 是否成功
     */
    @RequestMapping(value = "/removeGoods", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> removeGoods(Long goodsId) {
        Map<String, Object> result = new HashMap<>();
        if (goodsId != null && goodsId != 0) {
            if (goodsService.removeGoodsByGoodsId(goodsId)) {
                Map<String, Object> statusReturn = new HashMap<>();
                statusReturn.put("code", 1001);
                statusReturn.put("msg", "请求成功");
                result.put("status", statusReturn);
            } else {
                result = ResultUtil.fail(1004, "删除失败");
            }
        } else {
            result = ResultUtil.fail(1004, "入参有误");
        }
        return result;
    }

    /**
     * 将商品添加到专题
     *
     * @param goodsId 商品Id
     * @param themeId 专题Id
     * @return 返回请求结果
     */
    @RequestMapping(value = "/addGoodsToTheme", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> addGoodsToTheme(Long goodsId, Long themeId) {
        Map<String, Object> result = new HashMap<>();
        if (goodsId != null && goodsId != 0 && themeId != null && themeId != 0) {
            if (goodsService.goodsToTheme(goodsId, themeId)) {
                Map<String, Object> statusReturn = new HashMap<>();
                statusReturn.put("code", 1001);
                statusReturn.put("msg", "请求成功");
                result.put("status", statusReturn);
            } else {
                result = ResultUtil.fail(1004, "添加到专题失败");
            }
        } else {
            result = ResultUtil.fail(1004, "入参有误");
        }
        return result;
    }


    /**
     * 查询商品规格属性
     *
     * @return 返回查询结果
     */
    @RequestMapping(value = "/queryGoodsStandard", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryGoodsStandard() {
        Map<String, Object> result;
        List<GoodsStandardKV> data = goodsSortService.queryStandardKeyValue();
        if (data == null || data.size() == 0) {
            logger.warn("没有规格属性");
            return ResultUtil.fail(1004, "没有规格属性");
        }
        result = ResultUtil.success(data);
        return result;
    }


    /**
     * 校验商品信息
     *
     * @param goodsInfo 商品信息
     */
    private GoodsPOJO validateGoodsInfo(Map<String, Object> goodsInfo) {
        if (goodsInfo != null) {
            GoodsPOJO goods = new GoodsPOJO();
            GoodsStandardPOJO goodsStandard = new GoodsStandardPOJO();
            try {
                //类目信息
                Map catagoryInfo = (Map) goodsInfo.get("catatoryInfo");
                goods.setCatagory1((Integer) catagoryInfo.get("catagory1"));
                goods.setCatagory2((Integer) catagoryInfo.get("catagory2"));
                goods.setCatagory3((Integer) catagoryInfo.get("catagory3"));
                goods.setCatagory4((Integer) catagoryInfo.get("catagory4"));
                //基本属性
                Map basicInfo = (Map) goodsInfo.get("basicInfo");
                goods.setTitle((String) basicInfo.get("title"));
                goods.setSubtitle((String) basicInfo.get("subTitle"));
                goods.setGoodsDes((String) basicInfo.get("authorString"));//小编说
                goods.setShopPrice((int) ((double) basicInfo.get("sellPrice")) * 100);//转int存数据库 售价
                goods.setOriginPrice((int) ((double) basicInfo.get("originalPrice")) * 100);//转int存数据库 原价
                goods.setGoodsCost((int) ((double) basicInfo.get("costPrice")) * 100);//转int 成本价
                goods.setStockNum((Integer) basicInfo.get("storeNum"));//库存
                //图片信息
                Map picInfo = (Map) goodsInfo.get("picInfo");
                goods.setGoodsImg((String) picInfo.get("mainPic"));
                goods.setDetailImg(JSON.toJSONString(picInfo.get("detailPics")));
                goods.setCarouselImg(JSON.toJSONString(picInfo.get("carouselPics")));
                //上下架
                goods.setIsOnSale((Integer) goodsInfo.get("isPublish"));//0:下架 1:上架
                //商品规格属性
                Map propertyInfo = (Map) goodsInfo.get("propertyInfo");
                goodsStandard.setName((String) propertyInfo.get("name"));
                goodsStandard.setBrand((String) propertyInfo.get("brand"));
                goodsStandard.setProductPlace((String) propertyInfo.get("productPlace"));
                goodsStandard.setGoodsStandard((String) propertyInfo.get("standard"));
                goodsStandard.setNetContents((String) propertyInfo.get("netContents"));
                goodsStandard.setShelfLife((String) propertyInfo.get("shelfLife"));
                goodsStandard.setStorageMethod((String) propertyInfo.get("storageMethod"));
                goodsStandard.setIngredientList((String) propertyInfo.get("ingredientList"));
                goodsStandard.setIsSugary((String) propertyInfo.get("isSugary"));
                goodsStandard.setIsOrganic((String) propertyInfo.get("isOrganic"));
                goodsStandard.setIsImported((String) propertyInfo.get("isImported"));
                goodsStandard.setIsBoxPacked((String) propertyInfo.get("isBoxPacked"));
                goodsStandard.setPackageComponent((String) propertyInfo.get("packageComponent"));
                goodsStandard.setTaste((String) propertyInfo.get("taste"));
                goodsStandard.setIsBoxPacked((String) propertyInfo.get("facility"));
                goodsStandard.setUnsuitable((String) propertyInfo.get("unsuitable"));
                goodsStandard.setSuitable((String) propertyInfo.get("suitable"));
                goodsStandard.setProductForm((String) propertyInfo.get("productForm"));
                goodsStandard.setFoodAdditives((String) propertyInfo.get("foodAdditives"));
                goodsStandard.setSetCycle((String) propertyInfo.get("setCycle"));
                goodsStandard.setFactoryName((String) propertyInfo.get("factoryName"));
                goodsStandard.setFactorySite((String) propertyInfo.get("factorySite"));
                goodsStandard.setProductStandardNum((String) propertyInfo.get("productStandardNum"));
                goodsStandard.setFreshStoreTemp((String) propertyInfo.get("freshStoreTemp"));
                goodsStandard.setProof((String) propertyInfo.get("proof"));
                goodsStandard.setDegree((String) propertyInfo.get("degree"));
                goodsStandard.setAdaptiveScene((String) propertyInfo.get("adaptiveScene"));
                goodsStandard.setPackingMethod((String) propertyInfo.get("packingMethod"));
                goodsStandard.setPackingType((String) propertyInfo.get("packingType"));
                goodsStandard.setWineStyle((String) propertyInfo.get("wineStyle"));
                goodsStandard.setSuitSpecification((String) propertyInfo.get("suitSpecification"));
                goodsStandard.setDecanteDuration((String) propertyInfo.get("decanteDuration"));
                goodsStandard.setParticularYear((String) propertyInfo.get("particularYear"));
                goodsStandard.setSmell((String) propertyInfo.get("smell"));
                goodsStandard.setColourSort((String) propertyInfo.get("colourSort"));
                goodsStandard.setStyleType((String) propertyInfo.get("styleType"));
                goodsStandard.setSize((String) propertyInfo.get("size"));
                goodsStandard.setSpecialty((String) propertyInfo.get("specialty"));
                goodsStandard.setOther((String) propertyInfo.get("other"));
                goods.setGoodsStandardPOJO(goodsStandard);
                return goods;
            } catch (ClassCastException e) {
                logger.error("类目信息出错");
                return null;
            }
        }
        return null;
    }

    /**
     * 将商品信息转为页面参数
     *
     * @param goods 商品信息
     * @return map
     */
    private Map<String, Object> goodsToMap(GoodsPOJO goods) {
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> categoryInfo = new HashMap<>();
        Map<String, Object> basicInfo = new HashMap<>();
        Map<String, Object> picInfo = new HashMap<>();
        //类目信息
        categoryInfo.put("catagory1", goods.getCatagory1());
        categoryInfo.put("catagory2", goods.getCatagory2());
        categoryInfo.put("catagory3", goods.getCatagory3());
        categoryInfo.put("catagory4", goods.getCatagory4());
        //基本信息
        basicInfo.put("title", goods.getTitle());
        basicInfo.put("subTitle", goods.getSubtitle());
        basicInfo.put("authorString", goods.getGoodsDes());
        DecimalFormat df = new DecimalFormat("0.00");
        basicInfo.put("sellPrice", df.format(((double)goods.getShopPrice())/100));//价格要除以100
        basicInfo.put("originalPrice", df.format(((double)goods.getOriginPrice())/100));//价格要除以100
        basicInfo.put("costPrice", df.format(((double)goods.getGoodsCost())/100));//价格要除以100
        basicInfo.put("storeNum", goods.getStockNum());
        //图片
        picInfo.put("mainPic", goods.getGoodsImg());
        picInfo.put("carouselPics", JSON.parseArray(goods.getCarouselImg()));
        picInfo.put("detailPics", JSON.parseArray(goods.getDetailImg()));
        //上下架
        data.put("isOnsale", goods.getIsOnSale());
        data.put("categoryInfo", categoryInfo);
        data.put("basicInfo", basicInfo);
        //规格属性信息
        data.put("propertyInfo", goods.getGoodsStandardPOJO());
        data.put("picInfo", picInfo);
        return data;
    }
}

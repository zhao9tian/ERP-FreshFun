package com.quxin.freshfun.controller.goods;

import com.alibaba.fastjson.JSON;
import com.quxin.freshfun.model.goods.*;
import com.quxin.freshfun.service.goods.GoodsService;
import com.quxin.freshfun.service.goods.GoodsSortService;
import com.quxin.freshfun.service.goods.LimitedGoodsService;
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

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
    private LimitedGoodsService limitedGoodsService;

    @Autowired
    private GoodsSortService goodsSortService;

    private static DecimalFormat df = new DecimalFormat("0.00");

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
            if(goodsInfo.get("goodsId") != null){
                goodsPOJO.setGoodsId(Long.valueOf((Integer)goodsInfo.get("goodsId")));
                goodsPOJO.getGoodsStandardPOJO().setGoodsId(Long.valueOf((Integer)goodsInfo.get("goodsId")));
                if (goodsService.modifyGoods(goodsPOJO)) {
                    Map<String, Object> status = new HashMap<>();
                    status.put("code", 1001);
                    status.put("msg", "保存成功");
                    result.put("status", status);
                } else {
                    result = ResultUtil.fail(1004, "保存商品信息失败");
                }
            }else{
                if(!goodsService.isExistTitle(goodsPOJO.getTitle())){
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
     * @return 返回查询结果
     */
    @RequestMapping(value = "/goodsList", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryGoodsList(String subTitle , Integer category2 ,Integer isOnSale , Integer currentPage ,
                                              Integer pageSize ,Integer orderByCreate , Integer orderBySaleNum) {
        Map<String, Object> result;
        Map<String, Object> queryCondition = new HashMap<>();
        if(subTitle != null && !"".equals(subTitle.trim())){
            try {
                queryCondition.put("subTitle" ,new String(subTitle.getBytes("ISO-8859-1") ,"utf8"));
            } catch (UnsupportedEncodingException e) {
                logger.error("查询参数中文乱码");
            }
        }
        if(category2 != null && category2 != 0){
            queryCondition.put("category2" ,category2);
        }
        if(isOnSale != null){
            if(isOnSale == 1){
                queryCondition.put("isOnSale" ,1);
            }
            if(isOnSale == 2){
                queryCondition.put("isOnSale" ,0);
            }
        }
        if(currentPage != null){
            queryCondition.put("currentPage" ,currentPage);
        }
        if(pageSize != null){
            queryCondition.put("pageSize" ,pageSize);
        }
        if(orderByCreate != null){
            queryCondition.put("orderByCreate" ,orderByCreate);
        }
        if(orderBySaleNum != null){
            queryCondition.put("orderBySaleNum" ,orderBySaleNum);
        }
        List<GoodsPOJO> goods = goodsService.queryAllGoods(queryCondition);
        List<GoodsBaseOut> goodsBases = new ArrayList<>();
        if (goods != null) {
            Map<String, Integer> pageInfo = goodsService.queryPagingInfo(queryCondition);
            for (GoodsPOJO goodsPOJO : goods) {
                GoodsBaseOut goodsBase = new GoodsBaseOut();
                goodsBase.setGoodsId(goodsPOJO.getGoodsId());
                goodsBase.setImg(goodsPOJO.getGoodsImg());
                goodsBase.setShopPrice(MoneyFormatUtils.getMoneyFromInteger(goodsPOJO.getShopPrice()));//售价
                goodsBase.setOriginPrice(MoneyFormatUtils.getMoneyFromInteger(goodsPOJO.getOriginPrice()));//原价
                goodsBase.setCostPrice(MoneyFormatUtils.getMoneyFromInteger(goodsPOJO.getGoodsCost()));//成本价
                double grossMargin = ((double)(goodsPOJO.getShopPrice()-goodsPOJO.getGoodsCost()))/(double)goodsPOJO.getShopPrice();
                NumberFormat nf = NumberFormat.getPercentInstance();
                nf.setMaximumFractionDigits(1);//保留小数位-四舍五入
                goodsBase.setGrossMargin(nf.format(grossMargin));//毛利率
                goodsBase.setTitle(goodsPOJO.getTitle());
                goodsBase.setSubTitle(goodsPOJO.getSubtitle());
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
        Map<String , Object> goodsStandardMap = new HashMap<>();
        goodsStandardMap.put("goodsStandard" , data);
        if (data == null || data.size() == 0) {
            logger.warn("没有规格属性");
            return ResultUtil.fail(1004, "没有规格属性");
        }
        result = ResultUtil.success(goodsStandardMap);
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
                Map categoryInfo = (Map) goodsInfo.get("categoryInfo");
                goods.setCategory1((Integer) categoryInfo.get("category1"));
                goods.setCategory2((Integer) categoryInfo.get("category2"));
                goods.setCategory3((Integer) categoryInfo.get("category3"));
                goods.setCategory4((Integer) categoryInfo.get("category4"));
                //基本属性
                Map basicInfo = (Map) goodsInfo.get("basicInfo");
                goods.setTitle((String) basicInfo.get("title"));
                goods.setSubtitle((String) basicInfo.get("subTitle"));
                goods.setGoodsDes((String) basicInfo.get("authorString"));//小编说
                goods.setShopPrice(Math.round(Float.parseFloat((String) basicInfo.get("sellPrice")) * 100));//转int存数据库 售价
                goods.setOriginPrice(Math.round(Float.parseFloat((String) basicInfo.get("originalPrice")) * 100));//转int存数据库 原价
                goods.setGoodsCost(Math.round(Float.parseFloat((String) basicInfo.get("costPrice")) * 100));//转int 成本价
                goods.setStockNum((Integer) basicInfo.get("storeNum") );//库存
                //图片信息
                Map picInfo = (Map) goodsInfo.get("picInfo");
                goods.setGoodsImg((String) picInfo.get("mainPic"));
                goods.setDetailImg(JSON.toJSONString(picInfo.get("detailPics")));
                goods.setCarouselImg(JSON.toJSONString(picInfo.get("carouselPics")));
                //上下架
                goods.setIsOnSale((Integer) goodsInfo.get("isPublish"));//0:下架 1:上架
                //商品规格属性
                List properties = (List) goodsInfo.get("propertyInfo");
                for(Object object : properties){
                    Map propertyInfo = (Map) object; //50个属性
                    if("name".equals(propertyInfo.get("key"))){
                        goodsStandard.setName((String) propertyInfo.get("value"));
                    }
                    if("brand".equals(propertyInfo.get("key"))){
                        goodsStandard.setBrand((String) propertyInfo.get("value"));
                    }
                    if("productPlace".equals(propertyInfo.get("key"))){
                        goodsStandard.setProductPlace((String) propertyInfo.get("value"));
                    }
                    if("goodsStandard".equals(propertyInfo.get("key"))){
                        goodsStandard.setGoodsStandard((String) propertyInfo.get("value"));
                    }
                    if("netContents".equals(propertyInfo.get("key"))){
                        goodsStandard.setNetContents((String) propertyInfo.get("value"));
                    }
                    if("shelfLife".equals(propertyInfo.get("key"))){
                        goodsStandard.setShelfLife((String) propertyInfo.get("value"));
                    }
                    if("storageMethod".equals(propertyInfo.get("key"))){
                        goodsStandard.setStorageMethod((String) propertyInfo.get("value"));
                    }
                    if("ingredientList".equals(propertyInfo.get("key"))){
                        goodsStandard.setIngredientList((String) propertyInfo.get("value"));
                    }
                    if("isSugary".equals(propertyInfo.get("key"))){
                        goodsStandard.setIsSugary((String) propertyInfo.get("value"));
                    }
                    if("isOrganic".equals(propertyInfo.get("key"))){
                        goodsStandard.setIsOrganic((String) propertyInfo.get("value"));
                    }
                    if("isImported".equals(propertyInfo.get("key"))){
                        goodsStandard.setIsImported((String) propertyInfo.get("value"));
                    }
                    if("isBoxPacked".equals(propertyInfo.get("key"))){
                        goodsStandard.setIsBoxPacked((String) propertyInfo.get("value"));
                    }
                    if("packageComponent".equals(propertyInfo.get("key"))){
                        goodsStandard.setPackageComponent((String) propertyInfo.get("value"));
                    }
                    if("taste".equals(propertyInfo.get("key"))){
                        goodsStandard.setTaste((String) propertyInfo.get("value"));
                    }
                    if("facility".equals(propertyInfo.get("key"))){
                        goodsStandard.setFacility((String) propertyInfo.get("value"));
                    }
                    if("unsuitable".equals(propertyInfo.get("key"))){
                        goodsStandard.setUnsuitable((String) propertyInfo.get("value"));
                    }
                    if("suitable".equals(propertyInfo.get("key"))){
                        goodsStandard.setSuitable((String) propertyInfo.get("value"));
                    }
                    if("productForm".equals(propertyInfo.get("key"))){
                        goodsStandard.setProductForm((String) propertyInfo.get("value"));
                    }
                    if("foodAdditives".equals(propertyInfo.get("key"))){
                        goodsStandard.setFoodAdditives((String) propertyInfo.get("value"));
                    }
                    if("setCycle".equals(propertyInfo.get("key"))){
                        goodsStandard.setSetCycle((String) propertyInfo.get("value"));
                    }
                    if("factoryName".equals(propertyInfo.get("key"))){
                        goodsStandard.setFactoryName((String) propertyInfo.get("value"));
                    }
                    if("factorySite".equals(propertyInfo.get("key"))){
                        goodsStandard.setFactorySite((String) propertyInfo.get("value"));
                    }
                    if("productStandardNum".equals(propertyInfo.get("key"))){
                        goodsStandard.setProductStandardNum((String) propertyInfo.get("value"));
                    }
                    if("freshStoreTemp".equals(propertyInfo.get("key"))){
                        goodsStandard.setFreshStoreTemp((String) propertyInfo.get("value"));
                    }
                    if("proof".equals(propertyInfo.get("key"))){
                        goodsStandard.setProof((String) propertyInfo.get("value"));
                    }
                    if("degree".equals(propertyInfo.get("key"))){
                        goodsStandard.setDegree((String) propertyInfo.get("value"));
                    }
                    if("adaptiveScene".equals(propertyInfo.get("key"))){
                        goodsStandard.setAdaptiveScene((String) propertyInfo.get("value"));
                    }
                    if("packingMethod".equals(propertyInfo.get("key"))){
                        goodsStandard.setPackingMethod((String) propertyInfo.get("value"));
                    }
                    if("packingType".equals(propertyInfo.get("key"))){
                        goodsStandard.setPackingType((String) propertyInfo.get("value"));
                    }
                    if("wineStyle".equals(propertyInfo.get("key"))){
                        goodsStandard.setWineStyle((String) propertyInfo.get("value"));
                    }
                    if("suitSpecification".equals(propertyInfo.get("key"))){
                        goodsStandard.setSuitSpecification((String) propertyInfo.get("value"));
                    }
                    if("decanteDuration".equals(propertyInfo.get("key"))){
                        goodsStandard.setDecanteDuration((String) propertyInfo.get("value"));
                    }
                    if("particularYear".equals(propertyInfo.get("key"))){
                        goodsStandard.setParticularYear((String) propertyInfo.get("value"));
                    }
                    if("smell".equals(propertyInfo.get("key"))){
                        goodsStandard.setSmell((String) propertyInfo.get("value"));
                    }
                    if("colourSort".equals(propertyInfo.get("key"))){
                        goodsStandard.setColourSort((String) propertyInfo.get("value"));
                    }
                    if("styleType".equals(propertyInfo.get("key"))){
                        goodsStandard.setStyleType((String) propertyInfo.get("value"));
                    }
                    if("size".equals(propertyInfo.get("key"))){
                        goodsStandard.setSize((String) propertyInfo.get("value"));
                    }
                    if("specialty".equals(propertyInfo.get("key"))){
                        goodsStandard.setSpecialty((String) propertyInfo.get("value"));
                    }
                    if("agtron".equals(propertyInfo.get("key"))){
                        goodsStandard.setAgtron((String) propertyInfo.get("value"));
                    }
                    if("material".equals(propertyInfo.get("key"))){
                        goodsStandard.setMaterial((String) propertyInfo.get("value"));
                    }
                    if("coffeeType".equals(propertyInfo.get("key"))){
                        goodsStandard.setCoffeeType((String) propertyInfo.get("value"));
                    }
                    if("technics".equals(propertyInfo.get("key"))){
                        goodsStandard.setTechnics((String) propertyInfo.get("value"));
                    }
                    if("series".equals(propertyInfo.get("key"))){
                        goodsStandard.setSeries((String) propertyInfo.get("value"));
                    }
                    if("pattern".equals(propertyInfo.get("key"))){
                        goodsStandard.setPattern((String) propertyInfo.get("value"));
                    }
                    if("coffeeCookedDegree".equals(propertyInfo.get("key"))){
                        goodsStandard.setCoffeeCookedDegree((String) propertyInfo.get("value"));
                    }
                    if("color".equals(propertyInfo.get("key"))){
                        goodsStandard.setColor((String) propertyInfo.get("value"));
                    }
                    if("mouthfeel".equals(propertyInfo.get("key"))){
                        goodsStandard.setMouthfeel((String) propertyInfo.get("value"));
                    }
                    if("applicableObject".equals(propertyInfo.get("key"))){
                        goodsStandard.setApplicableObject((String) propertyInfo.get("value"));
                    }
                    if("bowlDiameter".equals(propertyInfo.get("key"))){
                        goodsStandard.setBowlDiameter((String) propertyInfo.get("value"));
                    }
                    if("other".equals(propertyInfo.get("key"))){
                        goodsStandard.setOther((String) propertyInfo.get("value"));
                    }
                }
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
        categoryInfo.put("category1", goods.getCategory1());
        categoryInfo.put("category2", goods.getCategory2());
        categoryInfo.put("category3", goods.getCategory3());
        categoryInfo.put("category4", goods.getCategory4());
        //基本信息
        basicInfo.put("title", goods.getTitle());
        basicInfo.put("subTitle", goods.getSubtitle());
        basicInfo.put("authorString", goods.getGoodsDes());
        basicInfo.put("sellPrice", df.format(((double)goods.getShopPrice())/100));//价格要除以100
        basicInfo.put("originalPrice", df.format(((double)goods.getOriginPrice())/100));//价格要除以100
        basicInfo.put("costPrice", df.format(((double)goods.getGoodsCost())/100));//价格要除以100
        basicInfo.put("storeNum", goods.getStockNum());
        //图片
        picInfo.put("mainPic", goods.getGoodsImg());
        picInfo.put("carouselPics", JSON.parseArray(goods.getCarouselImg()));
        picInfo.put("detailPics", JSON.parseArray(goods.getDetailImg()));
        //上下架
        data.put("isOnSale", goods.getIsOnSale());
        data.put("categoryInfo", categoryInfo);
        data.put("basicInfo", basicInfo);
        //规格属性信息
        data.put("propertyInfo", standardToList(goods.getGoodsStandardPOJO()));
        data.put("picInfo", picInfo);
        return data;
    }

    private List<Map> standardToList(GoodsStandardPOJO standard){
        List<Map> result = new ArrayList<>();
        if(standard != null){
            if(standard.getName() != null && !"".equals(standard.getName())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","name");
                map.put("value",standard.getName());
                result.add(map);
            }
            if(standard.getBrand() != null && !"".equals(standard.getBrand())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","brand");
                map.put("value",standard.getBrand());
                result.add(map);
            }
            if(standard.getProductPlace() != null && !"".equals(standard.getProductPlace())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","productPlace");
                map.put("value",standard.getProductPlace());
                result.add(map);
            }
            if(standard.getGoodsStandard() != null && !"".equals(standard.getGoodsStandard())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","goodsStandard");
                map.put("value",standard.getGoodsStandard());
                result.add(map);
            }
            if(standard.getNetContents() != null && !"".equals(standard.getNetContents())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","netContents");
                map.put("value",standard.getNetContents());
                result.add(map);
            }
            if(standard.getShelfLife() != null && !"".equals(standard.getShelfLife())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","shelfLife");
                map.put("value",standard.getShelfLife());
                result.add(map);
            }
            if(standard.getStorageMethod() != null && !"".equals(standard.getStorageMethod())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","storageMethod");
                map.put("value",standard.getStorageMethod());
                result.add(map);
            }
            if(standard.getIngredientList() != null && !"".equals(standard.getIngredientList())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","ingredientList");
                map.put("value",standard.getIngredientList());
                result.add(map);
            }
            if(standard.getIsSugary() != null && !"".equals(standard.getIsSugary())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","isSugary");
                map.put("value",standard.getIsSugary());
                result.add(map);
            }
            if(standard.getIsOrganic() != null && !"".equals(standard.getIsOrganic())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","isOrganic");
                map.put("value",standard.getIsOrganic());
                result.add(map);
            }
            if(standard.getIsImported() != null && !"".equals(standard.getIsImported())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","isImported");
                map.put("value",standard.getIsImported());
                result.add(map);
            }
            if(standard.getBrand() != null && !"".equals(standard.getBrand())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","isBoxPacked");
                map.put("value",standard.getIsBoxPacked());
                result.add(map);
            }
            if(standard.getPackageComponent() != null && !"".equals(standard.getPackageComponent())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","packageComponent");
                map.put("value",standard.getPackageComponent());
                result.add(map);
            }
            if(standard.getTaste() != null && !"".equals(standard.getTaste())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","taste");
                map.put("value",standard.getTaste());
                result.add(map);
            }
            if(standard.getFacility() != null && !"".equals(standard.getFacility())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","facility");
                map.put("value",standard.getFacility());
                result.add(map);
            }
            if(standard.getUnsuitable() != null && !"".equals(standard.getUnsuitable())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","unsuitable");
                map.put("value",standard.getUnsuitable());
                result.add(map);
            }
            if(standard.getSuitable() != null && !"".equals(standard.getSuitable())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","suitable");
                map.put("value",standard.getSuitable());
                result.add(map);
            }
            if(standard.getProductForm() != null && !"".equals(standard.getProductForm())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","productForm");
                map.put("value",standard.getProductForm());
                result.add(map);
            }
            if(standard.getFoodAdditives() != null && !"".equals(standard.getFoodAdditives())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","foodAdditives");
                map.put("value",standard.getFoodAdditives());
                result.add(map);
            }
            if(standard.getSetCycle() != null && !"".equals(standard.getSetCycle())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","setCycle");
                map.put("value",standard.getSetCycle());
                result.add(map);
            }
            if(standard.getFactoryName() != null && !"".equals(standard.getFactoryName())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","factoryName");
                map.put("value",standard.getFactoryName());
                result.add(map);
            }
            if(standard.getFactorySite() != null && !"".equals(standard.getFactorySite())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","factorySite");
                map.put("value",standard.getFactorySite());
                result.add(map);
            }
            if(standard.getProductStandardNum() != null && !"".equals(standard.getProductStandardNum())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","productStandardNum");
                map.put("value",standard.getProductStandardNum());
                result.add(map);
            }
            if(standard.getFreshStoreTemp() != null && !"".equals(standard.getFreshStoreTemp())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","freshStoreTemp");
                map.put("value",standard.getFreshStoreTemp());
                result.add(map);
            }
            if(standard.getProof() != null && !"".equals(standard.getProof())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","proof");
                map.put("value",standard.getProof());
                result.add(map);
            }
            if(standard.getDegree() != null && !"".equals(standard.getDegree())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","degree");
                map.put("value",standard.getDegree());
                result.add(map);
            }
            if(standard.getAdaptiveScene() != null && !"".equals(standard.getAdaptiveScene())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","adaptiveScene");
                map.put("value",standard.getAdaptiveScene());
                result.add(map);
            }
            if(standard.getPackingMethod() != null && !"".equals(standard.getPackingMethod())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","packingMethod");
                map.put("value",standard.getPackingMethod());
                result.add(map);
            }
            if(standard.getPackingType() != null && !"".equals(standard.getPackingType())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","packingType");
                map.put("value",standard.getPackingType());
                result.add(map);
            }
            if(standard.getWineStyle() != null && !"".equals(standard.getWineStyle())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","wineStyle");
                map.put("value",standard.getWineStyle());
                result.add(map);
            }
            if(standard.getSuitSpecification() != null && !"".equals(standard.getSuitSpecification())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","suitSpecification");
                map.put("value",standard.getSuitSpecification());
                result.add(map);
            }
            if(standard.getDecanteDuration() != null && !"".equals(standard.getDecanteDuration())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","decanteDuration");
                map.put("value",standard.getDecanteDuration());
                result.add(map);
            }
            if(standard.getParticularYear() != null && !"".equals(standard.getParticularYear())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","particularYear");
                map.put("value",standard.getParticularYear());
                result.add(map);
            }
            if(standard.getBrand() != null && !"".equals(standard.getBrand())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","smell");
                map.put("value",standard.getSmell());
                result.add(map);
            }
            if(standard.getColourSort() != null && !"".equals(standard.getColourSort())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","colourSort");
                map.put("value",standard.getColourSort());
                result.add(map);
            }
            if(standard.getStyleType() != null && !"".equals(standard.getStyleType())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","styleType");
                map.put("value",standard.getStyleType());
                result.add(map);
            }
            if(standard.getSize() != null && !"".equals(standard.getSize())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","size");
                map.put("value",standard.getSize());
                result.add(map);
            }
            if(standard.getSpecialty() != null && !"".equals(standard.getSpecialty())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","specialty");
                map.put("value",standard.getSpecialty());
                result.add(map);
            }
            if(standard.getAgtron() != null && !"".equals(standard.getAgtron())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","agtron");
                map.put("value",standard.getAgtron());
                result.add(map);
            }
            if(standard.getMaterial() != null && !"".equals(standard.getMaterial())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","material");
                map.put("value",standard.getMaterial());
                result.add(map);
            }
            if(standard.getCoffeeType() != null && !"".equals(standard.getCoffeeType())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","coffeeType");
                map.put("value",standard.getCoffeeType());
                result.add(map);
            }
            if(standard.getTechnics() != null && !"".equals(standard.getTechnics())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","technics");
                map.put("value",standard.getTechnics());
                result.add(map);
            }
            if(standard.getSeries() != null && !"".equals(standard.getSeries())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","series");
                map.put("value",standard.getSeries());
                result.add(map);
            }
            if(standard.getPattern() != null && !"".equals(standard.getPattern())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","pattern");
                map.put("value",standard.getPattern());
                result.add(map);
            }
            if(standard.getCoffeeCookedDegree() != null && !"".equals(standard.getCoffeeCookedDegree())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","coffeeCookedDegree");
                map.put("value",standard.getCoffeeCookedDegree());
                result.add(map);
            }
            if(standard.getColor() != null && !"".equals(standard.getColor())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","color");
                map.put("value",standard.getColor());
                result.add(map);
            }
            if(standard.getMouthfeel() != null && !"".equals(standard.getMouthfeel())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","mouthfeel");
                map.put("value",standard.getMouthfeel());
                result.add(map);
            }
            if(standard.getApplicableObject() != null && !"".equals(standard.getApplicableObject())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","applicableObject");
                map.put("value",standard.getApplicableObject());
                result.add(map);
            }
            if(standard.getBowlDiameter() != null && !"".equals(standard.getBowlDiameter())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","bowlDiameter");
                map.put("value",standard.getBowlDiameter());
                result.add(map);
            }
            if(standard.getOther() != null && !"".equals(standard.getOther())){
                Map<String , Object> map = new HashMap<>();
                map.put("key","other");
                map.put("value",standard.getOther());
                result.add(map);
            }
        }
        return result;
    }

    /**
     * 根据商品id获取售价
     * @param goodsId 商品id
     */
    @ResponseBody
    @RequestMapping("/getShopPriceByGoodsId")
    public Map<String,Object> getShopPriceByGoodsId(Long goodsId){
        if(goodsId==null||goodsId==0){
            logger.error("根据商品id查询活动商品信息时goodsId为空");
            return ResultUtil.fail(1004,"数据异常，请联系管理员");
        }
        Map<String , Object> map = new HashMap<String,Object>();
        LimitedGoodsPOJO limitedGoods =limitedGoodsService.queryLimitedGoodsById(goodsId);
        GoodsPOJO goods = goodsService.queryGoodsByGoodsId(goodsId);
        if(limitedGoods!=null&&limitedGoods.getLimitedPrice()!=null&&!"".equals(limitedGoods.getLimitedPrice())){
            Map<String,	String> contentMap = (Map) JSON.parse(limitedGoods.getLimitedPrice());
            String price = contentMap.get("discountPrice");
            Integer shopPrice = Integer.parseInt(price);
            map.put("shopMoney",MoneyFormatUtils.getMoneyFromInteger(shopPrice));
        }else{
            if(goods!=null&&goods.getShopPrice()!=null)
                map.put("shopMoney",MoneyFormatUtils.getMoneyFromInteger(goods.getShopPrice()));
            else
                map.put("shopMoney","0.00");
        }
        map.put("goodsImg",goods.getGoodsImg());
        map.put("goodsName",goods.getTitle());
        map.put("goodsDesc",goods.getSubtitle());
        map.put("marketMoney",MoneyFormatUtils.getMoneyFromInteger(goods.getOriginPrice()));
        return  ResultUtil.success(map);
    }
}

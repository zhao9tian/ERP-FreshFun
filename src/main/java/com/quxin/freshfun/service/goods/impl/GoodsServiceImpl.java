package com.quxin.freshfun.service.goods.impl;

import com.alibaba.fastjson.JSON;
import com.quxin.freshfun.dao.GoodsMapper;
import com.quxin.freshfun.model.goods.GoodsPOJO;
import com.quxin.freshfun.model.goods.GoodsStandardPOJO;
import com.quxin.freshfun.model.goods.ThemePOJO;
import com.quxin.freshfun.service.goods.GoodsService;
import com.quxin.freshfun.service.goods.GoodsThemeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品service实现类
 * Created by qucheng on 2016/10/18.
 */
@Service("goodsService")
public class GoodsServiceImpl implements GoodsService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsThemeService goodsThemeService;

    @Override
    public Boolean addGoods(GoodsPOJO goodsPOJO) {
        Boolean isInsert = false ;
        //0.检验参数
        if(validateParam(goodsPOJO)){
            //1.新增商品基本信息
            goodsPOJO.setSaleNum(0);//销量默认为0
            goodsPOJO.setCreated(System.currentTimeMillis()/1000);
            goodsPOJO.setUpdated(System.currentTimeMillis()/1000);
            Integer baseRecord = goodsMapper.insertGoodsBase(goodsPOJO);
            if(baseRecord == 1){
                //2.新增商品图片信息
                Integer imgRecord = goodsMapper.insertGoodsImg(goodsPOJO);
                //3.新增商品规格信息
                GoodsStandardPOJO goodsStandardPOJO = goodsPOJO.getGoodsStandardPOJO();
                goodsStandardPOJO.setGoodsId(goodsPOJO.getGoodsId());
                Integer standardRecord = goodsMapper.insertGoodsStandard(goodsPOJO.getGoodsStandardPOJO());
                if(imgRecord == 1 && standardRecord == 1 ){
                    isInsert = true;
                }else if(imgRecord != 1){
                    logger.error("保存商品图片失败");
                }else {
                    logger.error("保存商品规格失败");
                }
            }else{
                logger.error("保存商品基本信息失败");
            }
        }else{
            logger.error("商品参数校验不通过");
        }
        return isInsert;
    }

    @Override
    public GoodsPOJO queryGoodsByGoodsId(Long goodsId) {
        if(goodsId != null && goodsId != 0){
            GoodsPOJO goodsPOJO = goodsMapper.selectGoodsByGoodsId(goodsId);
            if(goodsPOJO != null){
                GoodsPOJO goodsImgPOJO = goodsMapper.selectGoodsImgByGoodsId(goodsId);
                if(goodsImgPOJO != null){
                    goodsPOJO.setDetailImg(goodsImgPOJO.getDetailImg());
                    goodsPOJO.setCarouselImg(goodsImgPOJO.getCarouselImg());
                }else{
                    logger.warn("商品详情或者轮播图片信息没有获取到");
                }
                GoodsStandardPOJO goodsStandardPOJO = goodsMapper.selectGoodsStandardByGoodsId(goodsId);
                if(goodsStandardPOJO != null){
                    goodsPOJO.setGoodsStandardPOJO(goodsStandardPOJO);
                }else{
                    logger.error("商品Id为"+goodsId+"的商品规格为空");
                }
                return goodsPOJO;
            }else{
                logger.error("商品id为:"+goodsId+"的商品不存在");
            }
        }else{
            logger.error("商品Id不能为空");
        }
        return null;
    }


    @Override
    public Boolean modifyGoods(GoodsPOJO goodsPOJO) {
        if (goodsPOJO.getGoodsId() != null && goodsPOJO.getGoodsId() != 0) {
            if(validateParam(goodsPOJO)){
                goodsPOJO.setUpdated(System.currentTimeMillis()/1000);
                Integer baseRecord = goodsMapper.updateGoods(goodsPOJO);
                Integer imgRecord = goodsMapper.updateGoodsImg(goodsPOJO);
                Integer standardRecord = goodsMapper.updateGoodsStandard(goodsPOJO.getGoodsStandardPOJO());
                if (baseRecord == 1 && imgRecord == 1 && standardRecord == 1) {
                    return true;
                } else {
                    logger.error("修改商品信息失败");
                }

            }else{
                logger.error("商品参数校验不通过");
            }
        } else {
            logger.error("商品Id不能为空");
        }
        return false;
    }

    @Override
    public Boolean removeGoodsByGoodsId(Long goodsId) {
        if (goodsId != null && goodsId != 0) {
            Integer num = goodsMapper.deleteGoodsByGoodsId(goodsId);
            if (num == 1) {
                return true;
            } else {
                logger.error("数据库没有该Id");
            }
        } else {
            logger.error("商品Id不能为空");
        }
        return false;
    }

    @Override
    public Boolean changeStatus(Long goodsId, Integer goodsSource, Integer status) {
        if (goodsId != null && goodsId != 0) {
            //根据商品来源判断是上下架B还是C端商品 C:10 b:20
            GoodsPOJO goods = new GoodsPOJO();
            goods.setGoodsId(goodsId);
            goods.setUpdated(System.currentTimeMillis() / 1000);
            if (goodsSource == 10) {//C端商品
                //获取商品的上下架信息
                if (1 == status) {
                    goods.setIsOnSale(1);
                } else if (2 == status) {
                    goods.setIsOnSale(0);
                } else {
                    logger.error("上下架状态为空");
                    return false;
                }
                Integer num = goodsMapper.updateGoods(goods);
                if (num == 1) {
                    return true;
                } else {
                    logger.error("商品不存在，C端上下架失败");
                }
            }
        } else {
            logger.error("商品Id不能为空");
        }
        return false;
    }

    @Override
    public Boolean goodsToTheme(Long goodsId, Long themeId) {
        if (themeId != null && themeId != 0) {
            if (goodsId != null && goodsId != 0) {
                ThemePOJO themePOJO = goodsThemeService.queryThemeByThemeId(themeId);
                if (themePOJO != null) {
                    String goodsIdList = themePOJO.getGoodsIdList();//获取json字符串
                    List<Long> themeIds = new ArrayList<>();
                    //如果原来的专题里面有商品,就判断加入的商品是否已经存在于专题,没有就不判断
                    if (goodsIdList != null && !"".equals(goodsIdList)) {
                        //将json字符串转为对象,判断新的商品Id是否存在于原来的专题
                        themeIds = JSON.parseArray(goodsIdList, Long.class);
                        if (themeIds.contains(goodsId)) {
                            logger.error("商品" + goodsId + "已存在于专题" + themeId);
                            return false;
                        }
                    }
                    themeIds.add(goodsId);
                    goodsIdList = JSON.toJSONString(themeIds);
                    themePOJO.setGoodsIdList(goodsIdList);
                    if (goodsThemeService.modifyTheme(themePOJO)) {
                        return true;
                    } else {
                        logger.error("商品" + goodsId + "加入专题" + themeId + "失败");
                    }
                }
            } else {
                logger.error("商品Id为空");
            }
        } else {
            logger.error("专题Id为空");
        }
        return false;
    }

    @Override
    public Boolean isExistTitle(String title) {
        Integer record = goodsMapper.selectCountByGoodsName(title);
        return record > 0;
    }

    @Override
    public Boolean changeGoodsImgs() {
        List<GoodsPOJO> goodsImgs = goodsMapper.selectGoodsImgs();
        String prefix = "http://pic1.freshfun365.com";
        for(GoodsPOJO goods : goodsImgs){
            List<String> details = new ArrayList<>();
            List<String> carousels = new ArrayList<>();
            String[] detailArr = goods.getDetailImg().split(",");
            String[] carouselArr = goods.getCarouselImg().split(",");
            GoodsPOJO goodsImg = new GoodsPOJO();
            for(String detail : detailArr){
                details.add(prefix+detail);
            }
            for(String carousel : carouselArr){
                carousels.add(prefix + carousel);
            }
            goodsImg.setGoodsId(goods.getGoodsId());
            goodsImg.setDetailImg(JSON.toJSONString(details));
            goodsImg.setCarouselImg(JSON.toJSONString(carousels));
            goodsMapper.updateGoodsImg(goodsImg);
        }
        return false;
    }


    @Override
    public List<GoodsPOJO> queryAllGoods(Map<String, Object> queryCondition) {
        Map<String , Integer> pagingInfo = queryPagingInfo(queryCondition);
        if(pagingInfo == null){
            return null;
        }
        Integer start = pagingInfo.get("start");
        Integer pageSize = pagingInfo.get("pageSize");
        //创建一个新的查询对象
        Map<String, Object> qc = new HashMap<>();
        //1.过滤查询条件
        String subtitle = (String) queryCondition.get("subTitle");
        Integer category2 = (Integer) queryCondition.get("category2");
        Integer isOnSale = (Integer) queryCondition.get("isOnSale");// 0:所有 1:上架 2:下架
        if (subtitle != null && !"".equals(subtitle.trim())) {
            qc.put("subTitle", subtitle);
        }
        if (category2 != null) {
            qc.put("category2", category2);
        }
        if (isOnSale != null) {
            qc.put("isOnSale", isOnSale);
        }
        //排序字段 -- 只按一个排序条件查询,没有排序条件就按创建时间默认倒序
        Integer orderByCreate = (Integer) queryCondition.get("orderByCreate");
        Integer orderBySaleNum = (Integer) queryCondition.get("orderBySaleNum");

        if(orderBySaleNum == null || orderBySaleNum == 0){
            if(orderByCreate == null || orderByCreate == 0 || orderByCreate == 1){
                qc.put("created" ,"desc");
            }else if(orderByCreate == 2){
                qc.put("created" ,"asc");
            }
        }else if(orderBySaleNum == 1){
            qc.put("saleNum" ,"desc");
        }else if(orderBySaleNum == 2){
            qc.put("saleNum" ,"asc");
        }
        qc.put("start",start);
        qc.put("pageSize",pageSize);
        return goodsMapper.selectGoodsList(qc);
    }

    @Override
    public Map<String , Integer> queryPagingInfo(Map<String, Object> queryCondition) {
        Map<String , Object> countQC = new HashMap<>();
        Map<String , Integer> pagingInfo = new HashMap<>();
        Integer currentPage = null;
        Integer pageSize = null;
        if(queryCondition != null){
            try {
                String subtitle = (String) queryCondition.get("subTitle");
                Integer category2 = (Integer) queryCondition.get("category2");
                Integer isOnSale = (Integer) queryCondition.get("isOnSale");// 0:所有 1:上架 2:下架
                currentPage = (Integer) queryCondition.get("currentPage");
                pageSize = (Integer) queryCondition.get("pageSize");
                if (subtitle != null && !"".equals(subtitle.trim())) {
                    countQC.put("subTitle", subtitle);
                }
                if (category2 != null) {
                    countQC.put("category2", category2);
                }
                if (isOnSale != null) {
                    countQC.put("isOnSale", isOnSale);
                }
            }catch (ClassCastException e){
                logger.error("查询条件输入有误");
                return null;
            }
        }
        Integer recordCount = goodsMapper.selectCountByQC(countQC);
        if(currentPage == null || currentPage == 0){
            currentPage = 1;
        }
        if(pageSize == null || pageSize == 0){
            pageSize = 10;
        }
        Integer totalPage = recordCount%pageSize == 0 ?  recordCount/pageSize : recordCount/pageSize + 1 ;
        Integer start = (currentPage - 1) * pageSize ;
        pagingInfo.put("start" , start);
        pagingInfo.put("pageSize" , pageSize);
        pagingInfo.put("totalPage" , totalPage);
        pagingInfo.put("total" , recordCount);
        pagingInfo.put("currentPage" , currentPage);
        return pagingInfo;
    }



    /**
     * 校验商品入参
     * @param goodsPOJO 商品对象
     * @return 校验是否通过
     */
    private Boolean validateParam(GoodsPOJO goodsPOJO){
        if(goodsPOJO != null){
            if(goodsPOJO.getCategory1() == null || goodsPOJO.getCategory1() == 0){
                logger.warn("一级类目为空");
                return false ;
            }
            if(goodsPOJO.getCategory2() == null || goodsPOJO.getCategory2() == 0){
                logger.warn("二级类目为空");
                return false ;
            }
            if(goodsPOJO.getCategory3() == null || goodsPOJO.getCategory3() == 0){
                logger.warn("三级类目为空");
                return false ;
            }
            if(goodsPOJO.getCategory4() == null || goodsPOJO.getCategory4() == 0){
                logger.warn("四级类目为空");
                return false ;
            }
            if(goodsPOJO.getIsOnSale() == null){
                logger.warn("上下架状态为空");
                return false ;
            }
            if(goodsPOJO.getShopPrice() == null){
                logger.warn("售价为空");
                return false ;
            }
            if(goodsPOJO.getOriginPrice() == null){
                logger.warn("原价为空");
                return false ;
            }
            if(goodsPOJO.getTitle() == null || "".equals(goodsPOJO.getTitle().trim())){
                logger.warn("标题为空");
                return false ;
            }
            if(goodsPOJO.getSubtitle() == null || "".equals(goodsPOJO.getSubtitle().trim())){
                logger.warn("副标题为空");
                return false ;
            }
            if(goodsPOJO.getGoodsDes() == null || "".equals(goodsPOJO.getGoodsDes().trim())){
                logger.warn("小编说内容为空");
                return false ;
            }
            if(goodsPOJO.getCarouselImg() == null || "".equals(goodsPOJO.getCarouselImg().trim())){
                logger.warn("商品轮播图为空");
                return false ;
            }
            if(goodsPOJO.getDetailImg() == null || "".equals(goodsPOJO.getDetailImg().trim())){
                logger.warn("商品详情图为空");
                return false ;
            }
            if(goodsPOJO.getGoodsCost() == null){
                logger.warn("商品成本价为空");
                return false ;
            }
            if(goodsPOJO.getStockNum() == null || goodsPOJO.getStockNum() == 0){
                logger.warn("库存为空");
                return false ;
            }
            if(goodsPOJO.getShopId() == null){
                logger.warn("商户Id为空");
                return false ;
            }
            if(goodsPOJO.getAppId() == null){
                logger.warn("appId为空");
                return false ;
            }
            if(goodsPOJO.getGoodsStandardPOJO() == null){
                logger.warn("商品规格为空");
                return false ;
            }
        }else{
            logger.warn("商品对象为空");
            return false ;
        }
        return true;
    }


}

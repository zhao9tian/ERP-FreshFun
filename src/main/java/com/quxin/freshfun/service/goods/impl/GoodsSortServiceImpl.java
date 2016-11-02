package com.quxin.freshfun.service.goods.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.quxin.freshfun.constant.GoodsConstant;
import com.quxin.freshfun.dao.GoodsSortMapper;
import com.quxin.freshfun.model.goods.*;
import com.quxin.freshfun.service.goods.GoodsService;
import com.quxin.freshfun.service.goods.GoodsSortService;
import com.quxin.freshfun.service.goods.GoodsThemeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 排序Service实现类
 * Created by qucheng on 2016/10/12.
 */
@Service("goodsSortService")
public class GoodsSortServiceImpl implements GoodsSortService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private GoodsSortMapper goodsSortMapper;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsThemeService goodsThemeService;

    @Override
    public List<GoodsPOJO> querySortGoods() {
        String sortValue = goodsSortMapper.selectGoodsPropertyValue(GoodsConstant.GOODS_SORT_KEY);
        if (sortValue == null || "".equals(sortValue)) {
            return null;
        }
        JSONArray sortArr = JSON.parseArray(sortValue);
        List<GoodsPOJO> sortList = new ArrayList<>();
        if (sortArr != null && sortArr.size() > 0) {
            for (Object goodsId : sortArr) {
                Integer goodsIdint = (Integer) goodsId;
                Long goodsLong = (long) goodsIdint;
                GoodsPOJO goods = goodsService.queryGoodsByGoodsId(goodsLong);
                if (goods == null) {
                    logger.error("商品Id为:" + goodsId + "的商品不存在或者已经下架");
                } else {
                    sortList.add(goods);
                }
            }
            return sortList;
        } else {
            return null;
        }
    }

    @Override
    public GoodsPOJO querySortGoodsById(Long goodId) {
        if (goodId == null) {
            logger.error("goodsId不能为空");
            return null;
        }
        return goodsService.queryGoodsByGoodsId(goodId);
    }

    @Override
    public Boolean addAllGoodsSort(List<Long> allSort) {
        Boolean b = false;
        if (allSort != null && allSort.size() > 0) {
            if (allSort.size() > 20) {
                logger.error("排序对象总数大于20");
            } else {
                for (Long goodsId : allSort) {
                    GoodsPOJO goods = goodsService.queryGoodsByGoodsId(goodsId);
                    if (goods == null) {
                        return false;
                    }
                }
                //判断是插入还是编辑
                String goodsSortValue = JSON.toJSONString(allSort);
                String sortValue = goodsSortMapper.selectGoodsPropertyValue(GoodsConstant.GOODS_SORT_KEY);
                if (sortValue != null) {
                    Integer num = goodsSortMapper.updateGoodsPropertyValue(GoodsConstant.GOODS_SORT_KEY, goodsSortValue);
                    if (num == 0) {
                        logger.error("编辑排序对象失败");
                        return false;
                    }
                } else {
                    Integer num = goodsSortMapper.insertGoodsPropertyValue(GoodsConstant.GOODS_SORT_KEY, goodsSortValue);
                    if (num == 0) {
                        logger.error("插入排序对象失败");
                        return false;
                    }
                }
                b = true;
            }
        } else {
            logger.error("排序对象为空");
        }
        return b;
    }

    @Override
    public Boolean addThemeSort(List<Long> themeSort) {
        if (themeSort != null && themeSort.size() != 0) {
            //校验参数是否有重复,轻度校验,不指出具体重复项
            Set<Long> set = new HashSet<>(themeSort);
            if (set.size() != themeSort.size()) {
                logger.error("排序专题Id重复,请检查入参");
                return false;
            }
            for (Long themeId : themeSort) {
                ThemePOJO theme = goodsThemeService.queryThemeByThemeId(themeId);
                if (theme == null) {
                    return false;
                }
            }
            //查询数据库是否有之前的排序决定是insert or update
            String themeSortValue = JSON.toJSONString(themeSort);
            String themeSortStr = goodsSortMapper.selectGoodsPropertyValue(GoodsConstant.THEME_SORT_KEY);
            if (themeSortStr == null) {
                goodsSortMapper.insertGoodsPropertyValue(GoodsConstant.THEME_SORT_KEY, themeSortValue);
            } else {
                goodsSortMapper.updateGoodsPropertyValue(GoodsConstant.THEME_SORT_KEY, themeSortValue);
            }
            return true;
        } else {
            logger.error("专题排序内容为空");
        }
        return false;
    }

    @Override
    public List<ThemePOJO> queryThemeSort() {
        List<ThemePOJO> themeSort = new ArrayList<>();
        String themeSortStr = goodsSortMapper.selectGoodsPropertyValue(GoodsConstant.THEME_SORT_KEY);
        if (themeSortStr != null && !"".equals(themeSortStr)) {
            List<Long> themeIds = JSON.parseArray(themeSortStr, Long.class);//转list比Array更加方便,如果里面是对象的话
            for (Long themeId : themeIds) {
                ThemePOJO theme = goodsThemeService.queryThemeByThemeId(themeId);
                if (theme != null) {
                    themeSort.add(theme);
                }
            }
        } else {
            logger.warn("未查询到专题列表信息");
            return null;
        }
        return themeSort;
    }

    @Override
    public Boolean addSelectionSort(List<Map<String, Object>> selectionSort) throws ClassCastException {
        //校验入参
        if (selectionSort != null && selectionSort.size() != 0) {
            Set<Map<String, Object>> set = new HashSet<>(selectionSort);
            if (set.size() != selectionSort.size()) {
                logger.error("精选排序入参有重复");
                return false;
            }
            for (Map<String, Object> selection : selectionSort) {
                try {
                    Long goodsId = (Long) selection.get(GoodsConstant.SELECTION_GOODSID_KEY);
                    GoodsPOJO goods = goodsService.queryGoodsByGoodsId(goodsId);
                    if (goods == null) {
                        return false;
                    }
                } catch (ClassCastException e) {
                    logger.error("商品Id类型不为Long", e);
                    return false;
                }
            }
            String selectionSortJson = JSON.toJSONString(selectionSort);
            String selectionSortFromDB = goodsSortMapper.selectGoodsPropertyValue(GoodsConstant.SELECTION_SORT_KEY);
            if (selectionSortFromDB == null) {
                goodsSortMapper.insertGoodsPropertyValue(GoodsConstant.SELECTION_SORT_KEY, selectionSortJson);
            } else {
                goodsSortMapper.updateGoodsPropertyValue(GoodsConstant.SELECTION_SORT_KEY, selectionSortJson);
            }
            return true;
        } else {
            logger.error("精选入参为空");
        }
        return false;
    }

    @Override
    public List<GoodsSelectionPOJO> querySelectionSort() {
        List<GoodsSelectionPOJO> goodsSelections = new ArrayList<>();
        String goodsSelectionFromDB = goodsSortMapper.selectGoodsPropertyValue(GoodsConstant.SELECTION_SORT_KEY);
        if (goodsSelectionFromDB != null && !"".equals(goodsSelectionFromDB)) {
            List<Map> selectionJSON = JSON.parseArray(goodsSelectionFromDB, Map.class);
            for (Map selection : selectionJSON) {
                //此处从数据库用fastjson解析出来的id默认为Integer类型,且不能用long转化
                Long goodsId = Long.valueOf((Integer) selection.get(GoodsConstant.SELECTION_GOODSID_KEY));
                GoodsPOJO goods = goodsService.queryGoodsByGoodsId(goodsId);
                if (goods != null) {
                    GoodsSelectionPOJO goodsSelection = new GoodsSelectionPOJO();
                    goodsSelection.setGoodsPOJO(goods);
                    goodsSelection.setSelectionImg((String) selection.get(GoodsConstant.SELECTION_IMG_KEY));
                    goodsSelections.add(goodsSelection);
                }
            }
        } else {
            logger.error("数据库里面没有精选数据");
            return null;
        }
        return goodsSelections;
    }

    @Override
    public Boolean addBannerSort(List<Map<String, Object>> bannerSort) {
        //校验入参 null 去重 是否存在
        if (bannerSort != null && bannerSort.size() > 0) {
            List<Long> themeIds = new ArrayList<>();
            for (Map<String, Object> banner : bannerSort) {
                Long themeId = (Long) banner.get(GoodsConstant.BANNER_THEMEID_KEY);
                ThemePOJO theme = goodsThemeService.queryThemeByThemeId(themeId);
                if (theme == null)
                    return false;
                themeIds.add(themeId);
            }
            Set<Long> set = new HashSet<>(themeIds);
            if (set.size() != bannerSort.size()) {
                logger.error("banner入参themeId重复");
                return false;
            }
            String bannerSortValue = JSON.toJSONString(bannerSort);
            String bannerSortFromDB = goodsSortMapper.selectGoodsPropertyValue(GoodsConstant.BANNER_SORT_KEY);
            if (bannerSortFromDB == null) {
                goodsSortMapper.insertGoodsPropertyValue(GoodsConstant.BANNER_SORT_KEY, bannerSortValue);
            } else {
                goodsSortMapper.updateGoodsPropertyValue(GoodsConstant.BANNER_SORT_KEY, bannerSortValue);
            }
            return true;
        } else {
            logger.error("banner入参为空");
        }
        return false;
    }

    @Override
    public List<BannerPOJO> queryBannerSort() {
        List<BannerPOJO> bannerSort = new ArrayList<>();
        String bannerSortFromDB = goodsSortMapper.selectGoodsPropertyValue(GoodsConstant.BANNER_SORT_KEY);
        if (bannerSortFromDB != null && !"".equals(bannerSortFromDB)) {
            List<Map> bannerSortJSON = JSON.parseArray(bannerSortFromDB, Map.class);
            for (Map banner : bannerSortJSON) {
                Long themeId = Long.valueOf((Integer) banner.get(GoodsConstant.BANNER_THEMEID_KEY));
                ThemePOJO theme = goodsThemeService.queryThemeByThemeId(themeId);
                if (theme != null) {
                    BannerPOJO bannerPOJO = new BannerPOJO();
                    bannerPOJO.setThemePOJO(theme);
                    bannerPOJO.setBannerImg((String) banner.get(GoodsConstant.BANNER_IMG_KEY));
                    bannerPOJO.setUrl((String) banner.get(GoodsConstant.BANNER_URL_KEY));
                    bannerSort.add(bannerPOJO);
                }
            }
        } else {
            logger.error("数据库没有banner排序的数据");
        }
        return bannerSort;
    }

    @Override
    public List<GoodsStandardKV> queryStandardKeyValue() {
        String standardFromDB = goodsSortMapper.selectGoodsPropertyValue(GoodsConstant.GOODS_STANDARD_KEY);
        List<GoodsStandardKV> standards = new ArrayList<>();
        if(standardFromDB!= null && !"".equals(standardFromDB)){
            try{
                List<Map> standardJSON = JSON.parseArray(standardFromDB , Map.class);
                for(Map map : standardJSON){
                    GoodsStandardKV goodsStandardKV = new GoodsStandardKV();
                    goodsStandardKV.setKey((String) map.get("key"));
                    goodsStandardKV.setName((String) map.get("name"));
                    standards.add(goodsStandardKV);
                }
            }catch (Exception e){
                logger.error("规格属性数据格式有误" , e);
                return standards;
            }
        }else{
            logger.warn("数据库没有规格属性数据");
        }
        return standards;
    }

}

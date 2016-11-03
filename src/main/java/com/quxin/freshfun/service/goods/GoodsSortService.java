package com.quxin.freshfun.service.goods;

import com.quxin.freshfun.model.goods.*;

import java.util.List;
import java.util.Map;

/**
 * 商品排序Service
 * Created by qucheng on 2016/10/12.
 */
public interface GoodsSortService {

    /**
     * 查询已经排序的商品，要求商品数量20
     * @return 排序商品列表
     */
    List<GoodsPOJO> querySortGoods();

    /**
     * 根据Id 查询未下架商品信息
     * @param goodsId 商品ID
     * @return 返回商品基本信息
     */
    GoodsPOJO querySortGoodsById(Long goodsId);

    /**
     * 保存页面所有排序
     * @param allSort 排序对象
     * @return 返回是否插入成功
     */
    Boolean addAllGoodsSort(List<Long> allSort);

    /**
     * 保存专题排序
     * @param themeSort 专题排序
     * @return 是否保存成功
     */
    Boolean addThemeSort(List<Long> themeSort);

    /**
     * 查询专题排序
     * @return 返回排序结果
     */
    List<ThemePOJO> queryThemeSort();

    /**
     * 保存精选排序和图片内容
     * @param selectionSort 精选内容
     * @return 是否保存成功
     */
    Boolean addSelectionSort(List<Map<String , Object>> selectionSort);

    /**
     * 查询排序的商品信息和精选图片
     * @return 返回查询信息
     */
    List<GoodsSelectionPOJO> querySelectionSort();

    /**
     * 保存banner排序
     * @param bannerSort banner排序 对象[{themeId : 1L ,img : "http:...jpg" ,url : "暂时不用"},{}]
     * @return 是否成功
     */
    Boolean addBannerSort(List<Map<String , Object>> bannerSort);

    /**
     * 查询banner排序
     * @return banner 排序列表
     */
    List<BannerPOJO> queryBannerSort();

    /**
     * 查询商品规格属性键值对
     * @return 商品规格属性
     */
    List<GoodsStandardKV> queryStandardKeyValue();
}

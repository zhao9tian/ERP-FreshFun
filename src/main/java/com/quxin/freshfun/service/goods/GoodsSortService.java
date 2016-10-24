package com.quxin.freshfun.service.goods;

import com.quxin.freshfun.model.goods.GoodsPOJO;

import java.util.List;

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
    Boolean addAllGoodsSort(List<Integer> allSort);
}

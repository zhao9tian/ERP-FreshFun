package com.quxin.freshfun.dao;


import com.quxin.freshfun.model.goods.LimitedGoodsPOJO;

import java.util.List;

/**
 * 商品dao层
 */
public interface LimitedGoodsMapper {

    /**
     * 插入限量购商品
     * @param limitedGoodsPOJO 限量购商品
     * @return 插入条数
     */
    Integer insertLimitedGoods(LimitedGoodsPOJO limitedGoodsPOJO);

    /**
     * 修改限量购商品
     * @param limitedGoodsPOJO 限量购商品
     * @return 修改条数
     */
    Integer updateLimitedGoods(LimitedGoodsPOJO limitedGoodsPOJO);

    /**
     * 删除限量购商品
     * @param limitedGoodsId 限量购商品Id
     * @return 删除记录数
     */
    Integer deletedLimitedGoods(Long limitedGoodsId);

    /**
     * 查询限量购列表
     * @return 限量购商品列表
     */
    List<LimitedGoodsPOJO> selectLimitedGoods();

    /**
     * 根据限量购Id查询限量购商品
     * @param limitedGoodsId 限量购商品id
     * @return 限量购商品信息
     */
    LimitedGoodsPOJO selectLimitedGoodsByLimitedGoodsId(Long limitedGoodsId);
}
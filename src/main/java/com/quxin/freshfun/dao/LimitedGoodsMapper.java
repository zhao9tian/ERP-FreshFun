package com.quxin.freshfun.dao;


import com.quxin.freshfun.model.goods.LimitedGoodsPOJO;
import org.apache.ibatis.annotations.Param;

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
     * 根据限量购Id查询限量购商品
     * @param limitedGoodsId 限量购商品id
     * @return 限量购商品信息
     */
    LimitedGoodsPOJO selectLimitedGoodsByLimitedGoodsId(Long limitedGoodsId);

    /**
     * 查询限量购列表
     * @param ids 排序限量购Ids
     * @return 限量购商品
     */
    List<LimitedGoodsPOJO> selectLimitedGoodsList(@Param("limitedGoodsIds") List<Long> ids);

    /**
     * 销量返还给商品
     * @param i 销量
     * @return 修改结果
     */
    Integer returnSaleNum(@Param("returnSaleNum") int i , @Param("goodsId") Long goodsId);
}
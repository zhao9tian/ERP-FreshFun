package com.quxin.freshfun.dao;

import com.quxin.freshfun.model.goods.GoodsPOJO;
import com.quxin.freshfun.model.goods.GoodsStandardPOJO;

/**
 * 商品dao层
 */
public interface GoodsMapper {

    /**
     * 删除商品 修改is_deleted
     * @param goodsId 商品id
     * @return 删除记录数
     */
    Integer deleteGoodsByGoodsId(Long goodsId);

    /**
     * 修改商品
     * @param goodsPOJO 商品信息
     * @return 修改条数
     */
    Integer updateGoods(GoodsPOJO goodsPOJO);

    /**
     * 根据商品Id查询商品详情
     * @param goodsId 商品Id
     * @return 返回商品信息
     */
    GoodsPOJO selectGoodsByGoodsId(Long goodsId);

    /**
     * 根据商品Id查询商品规格
     * @param goodsId 商品规格
     * @return 返回规格实体
     */
    GoodsStandardPOJO selectGoodsStandardByGoodsId(Long goodsId);
}
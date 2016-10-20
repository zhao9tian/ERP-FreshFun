package com.quxin.freshfun.service.goods;

import com.quxin.freshfun.model.goods.GoodsPOJO;

/**
 * 商品service
 * Created by qucheng on 2016/10/18.
 */
public interface GoodsService {


    /**
     * 保存商品信息
     * @param goodsPOJO 商品对应实体
     * @return 是否插入成功
     */
    Boolean addGoods(GoodsPOJO goodsPOJO);

    /**
     * 根据商品Id查询商品信息
     * @param goodsId 商品Id
     * @return 商品所有信息
     */
    GoodsPOJO selectGoodsByGoodsId(Integer goodsId);

//    List<>
}

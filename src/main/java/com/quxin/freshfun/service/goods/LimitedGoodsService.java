package com.quxin.freshfun.service.goods;

import com.quxin.freshfun.model.goods.LimitedGoodsPOJO;

import java.util.List;

/**
 * 限量购service
 * Created by qucheng on 16/12/14.
 */
public interface LimitedGoodsService {


    /**
     * 批量加入限量购商品
     * @param limitedGoodsPOJOs 限量购商品
     * @return 是否成功
     */
    Boolean addBatchLimitedGoods(List<LimitedGoodsPOJO> limitedGoodsPOJOs);

    /**
     * 插入限量购商品
     * @param limitedGoodsPOJO 限量购商品
     * @return 是否插入成功
     */
    Boolean addLimitedGoods(LimitedGoodsPOJO limitedGoodsPOJO);

    /**
     * 编辑限量购商品信息
     * @param limitedGoodsPOJO 编辑限量购商品
     * @return 是否编辑成功
     */
    Boolean modifyLimitedGoods(LimitedGoodsPOJO limitedGoodsPOJO);

    /**
     * 查询限量购商品列表
     * @return 限量购商品列表
     */
    List<LimitedGoodsPOJO> queryLimitedGoodsList();

    /**
     * 删除限量购商品
     * @param limitedGoodsId 限量购商品Id
     * @return 是否删除成功
     */
    Boolean deletedLimitedGoods(Long limitedGoodsId);

    /**
     * 根据goodsId查询商品
     * @param goodsId 商品id
     * @return 活动商品信息
     * */
    LimitedGoodsPOJO queryLimitedGoodsById(Long goodsId);
}

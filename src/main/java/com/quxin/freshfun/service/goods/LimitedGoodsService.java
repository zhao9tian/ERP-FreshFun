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

}

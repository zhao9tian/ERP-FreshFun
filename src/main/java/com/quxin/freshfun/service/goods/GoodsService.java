package com.quxin.freshfun.service.goods;

import com.quxin.freshfun.model.goods.GoodsPOJO;

import java.util.List;
import java.util.Map;

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
    GoodsPOJO queryGoodsByGoodsId(Long goodsId);

    /**
     * 根据查询条件查询商品列表
     * @param queryCondition 查询条件 排序 搜索 分页
     * @return 商品列表
     */
    List<GoodsPOJO> queryAllGoods(Map<String , Object> queryCondition);

    /**
     * 修改商品信息
     * @param goodsPOJO 商品信息
     * @return 修改是否成功
     */
    Boolean modifyGoods(GoodsPOJO goodsPOJO);

    /**
     * 根据商品Id删除商品
     * @param goodsId 商品Id
     * @return 删除是否成功
     */
    Boolean removeGoodsByGoodsId(Long goodsId);

    /**
     * BC端商品上下架
     * @param goodsId 商品Id
     * @param goodsSource 商品来自B还是C
     * @return 上下架是否成功
     */
    Boolean goodsOnOrOff(Long goodsId , Integer goodsSource);
}

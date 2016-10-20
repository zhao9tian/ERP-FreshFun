package com.quxin.freshfun.service.goods;

import com.quxin.freshfun.model.goods.GoodsCategoryOut;
import com.quxin.freshfun.model.goods.GoodsPOJO;

import java.util.List;

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


}

package com.quxin.freshfun.service.goods;

import com.quxin.freshfun.model.goods.GoodsCategoryOut;

import java.util.List;

/**
 * 商品service
 * Created by qucheng on 2016/10/18.
 */
public interface GoodsService {

    List<GoodsCategoryOut> getCategoryList();
}

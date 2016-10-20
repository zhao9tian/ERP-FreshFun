package com.quxin.freshfun.service.goods.goodsimpl;

import com.quxin.freshfun.dao.GoodsMapper;
import com.quxin.freshfun.model.goods.GoodsPOJO;
import com.quxin.freshfun.service.goods.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商品service实现类
 * Created by qucheng on 2016/10/18.
 */
@Service("goodsService")
public class GoodsServiceImpl implements GoodsService{

    @Autowired
    private GoodsMapper goodsMapper;


    @Override
    public Boolean addGoods(GoodsPOJO goodsPOJO) {
        return null;
    }

    @Override
    public GoodsPOJO selectGoodsByGoodsId(Integer goodsId) {
        return null;
    }


}

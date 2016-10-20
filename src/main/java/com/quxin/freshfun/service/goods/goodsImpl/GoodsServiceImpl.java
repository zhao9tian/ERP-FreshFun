package com.quxin.freshfun.service.goods.goodsImpl;

import com.quxin.freshfun.dao.GoodsMapper;
import com.quxin.freshfun.model.goods.GoodsCategoryOut;
import com.quxin.freshfun.model.goods.GoodsPOJO;
import com.quxin.freshfun.service.goods.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}

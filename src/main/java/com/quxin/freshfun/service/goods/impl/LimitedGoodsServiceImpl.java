package com.quxin.freshfun.service.goods.impl;

import com.quxin.freshfun.dao.LimitedGoodsMapper;
import com.quxin.freshfun.model.goods.LimitedGoodsPOJO;
import com.quxin.freshfun.service.goods.LimitedGoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Ziming on 2016/12/14.
 */
@Service("limitedGoodsService")
public class LimitedGoodsServiceImpl implements LimitedGoodsService{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private LimitedGoodsMapper limitedGoodsMapper;
    @Override
    public Boolean addBatchLimitedGoods(List<LimitedGoodsPOJO> limitedGoodsPOJOs) {
        return null;
    }

    @Override
    public List<LimitedGoodsPOJO> queryLimitedGoodsList() {
        return null;
    }

    @Override
    public Boolean deletedLimitedGoods(Long limitedGoodsId) {
        return null;
    }

    @Override
    public LimitedGoodsPOJO queryLimitedGoodsById(Long goodsId) {
        if(goodsId==null||goodsId==0){
            logger.error("根据商品id查询活动商品信息时goodsId为空");
            return null;
        }
        return limitedGoodsMapper.selectLimitedGoodsByLimitedGoodsId(goodsId);
    }
}

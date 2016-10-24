package com.quxin.freshfun.service.goods.impl;

import com.quxin.freshfun.dao.GoodsMapper;
import com.quxin.freshfun.model.goods.GoodsPOJO;
import com.quxin.freshfun.model.goods.GoodsStandardPOJO;
import com.quxin.freshfun.service.goods.GoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 商品service实现类
 * Created by qucheng on 2016/10/18.
 */
@Service("goodsService")
public class GoodsServiceImpl implements GoodsService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

//    @Autowired
//    private GoodsService goodsService;

    @Autowired
    private GoodsMapper goodsMapper;


    @Override
    public Boolean addGoods(GoodsPOJO goodsPOJO) {

        return null;
    }

    @Override
    public GoodsPOJO queryGoodsByGoodsId(Long goodsId) {
        if(goodsId != null && goodsId != 0){
            GoodsPOJO goodsPOJO = goodsMapper.selectGoodsByGoodsId(goodsId);
            if(goodsPOJO != null){
                GoodsStandardPOJO goodsStandardPOJO = goodsMapper.selectGoodsStandardByGoodsId(goodsId);
                if(goodsStandardPOJO != null){
                    goodsPOJO.setGoodsStandardPOJO(goodsStandardPOJO);
                }else{
                    logger.error("商品Id为"+goodsId+"的商品规格为空");
                }
                return goodsPOJO;
            }else{
                logger.error("商品id为:"+goodsId+"的商品不存在");
            }
        }else{
            logger.error("商品Id不能为空");
        }
        return null;
    }

    @Override
    public List<GoodsPOJO> queryAllGoods(Map<String, Object> queryCondition) {
        return null;
    }

    @Override
    public Boolean modifyGoods(GoodsPOJO goodsPOJO) {
        if (goodsPOJO.getGoodsId() != null && goodsPOJO.getGoodsId() != 0) {
            Integer num = goodsMapper.updateGoods(goodsPOJO);
            if (num == 1) {
                return true;
            } else {
                logger.error("修改商品信息失败");
            }
        } else {
            logger.error("商品Id不能为空");
        }
        return false;
    }

    @Override
    public Boolean removeGoodsByGoodsId(Long goodsId) {
        if (goodsId != null && goodsId != 0) {
            Integer num = goodsMapper.deleteGoodsByGoodsId(goodsId);
            if (num == 1) {
                return true;
            } else {
                logger.error("数据库没有该Id");
            }
        } else {
            logger.error("商品Id不能为空");
        }
        return false;
    }

    @Override
    public Boolean goodsOnOrOff(Long goodsId , Integer goodsSource) {
        GoodsPOJO goodsPOJO ;
        if(goodsId != null && goodsId != 0){
            goodsPOJO = queryGoodsByGoodsId(goodsId);
            if(goodsPOJO != null){
                //根据商品来源判断是上下架B还是C端商品 C:10 b:20
                GoodsPOJO goods = new GoodsPOJO();
                goods.setGoodsId(goodsId);
                if(goodsSource == 10){//C端商品
                    //获取商品的上下架信息
                    Integer isOnsale = goodsPOJO.getIsOnSale();
                    if(isOnsale == 0){
                        goods.setIsOnSale(1);
                    }else{
                        goods.setIsOnSale(0);
                    }
                    Integer num = goodsMapper.updateGoods(goods);
                    if(num == 1){
                        return  true ;
                    }else{
                        logger.error("C端上下架失败");
                    }
                }else if(goodsSource == 20){//B端商品
                    Integer isBSale = goodsPOJO.getIsBSale();
                    if(isBSale == 0){
                        goods.setIsBSale(1);
                    }else{
                        goods.setIsBSale(0);
                    }
                    Integer num = goodsMapper.updateGoods(goods);
                    if(num == 1){
                        return  true ;
                    }else{
                        logger.error("B端上下架失败");
                    }
                }
            }
        }else{
            logger.error("商品Id不能为空");
        }
        return false;
    }


}

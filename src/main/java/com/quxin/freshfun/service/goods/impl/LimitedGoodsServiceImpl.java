package com.quxin.freshfun.service.goods.impl;

import com.alibaba.fastjson.JSON;
import com.quxin.freshfun.constant.GoodsConstant;
import com.quxin.freshfun.dao.GoodsSortMapper;
import com.quxin.freshfun.dao.LimitedGoodsMapper;
import com.quxin.freshfun.model.goods.GoodsPOJO;
import com.quxin.freshfun.model.goods.LimitedGoodsPOJO;
import com.quxin.freshfun.service.goods.GoodsService;
import com.quxin.freshfun.service.goods.LimitedGoodsService;
import com.quxin.freshfun.utils.GoodsSortUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 限时商品service实现类
 * Created by Ziming on 2016/12/14.
 * updated by qucheng on 2016/12/15.
 */
@Service("limitedGoodsService")
public class LimitedGoodsServiceImpl implements LimitedGoodsService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private LimitedGoodsMapper limitedGoodsMapper;

    @Autowired
    private GoodsSortMapper goodsSortMapper;

    @Autowired
    private GoodsService goodsService;

    @Override
    public Boolean addBatchLimitedGoods(List<LimitedGoodsPOJO> limitedGoodsPOJOs) {
        if (limitedGoodsPOJOs != null) {
            Set<Long> set = new HashSet<>();
            for(LimitedGoodsPOJO limitedGoodsPOJO : limitedGoodsPOJOs){
                set.add(limitedGoodsPOJO.getLimitedGoodsId());
            }
            if (limitedGoodsPOJOs.size() != set.size()) {
                logger.error("限量购商品id有重复");
                return false;
            }
            List<Long> limitedGoodsIds = new ArrayList<>();
            for (LimitedGoodsPOJO limitedGoodsPOJO : limitedGoodsPOJOs) {
                //校验商品存在，是否下架
                GoodsPOJO goodsPOJO = goodsService.queryGoodsBaseByGoodsId(limitedGoodsPOJO.getLimitedGoodsId());
                if (goodsPOJO == null) {
                    logger.error("商品Id为:" + limitedGoodsPOJO.getLimitedGoodsId() + "不存在");
                } else {
                    if (goodsPOJO.getIsOnSale() == 0) {
                        logger.error("商品Id为:" + limitedGoodsPOJO.getLimitedGoodsId() + "的商品已下架");
                    } else {
                        //根据Id判断是否修改或新增
                        LimitedGoodsPOJO limitedGoods = queryLimitedGoodsById(limitedGoodsPOJO.getLimitedGoodsId());
                        //校验商品新填的库存是否大于已有库存
                        Integer oldStock = goodsPOJO.getStockNum()-goodsPOJO.getSaleNum();
                        if ( limitedGoods!= null) {
                            if(limitedGoodsPOJO.getLimitedStock() > oldStock + limitedGoods.getLimitedStock()){
                                logger.error("限量购库存大于商品原有库存");
                                return false ;
                            }else{
                                if (!modifyLimitedGoods(limitedGoodsPOJO)) {
                                    logger.error("编辑限量购失败:" + JSON.toJSONString(limitedGoodsPOJO));
                                }
                                limitedGoodsIds.add(limitedGoodsPOJO.getLimitedGoodsId());
                            }
                        } else {
                            if(limitedGoodsPOJO.getLimitedStock() > oldStock){
                                logger.error("限量购库存大于商品原有库存");
                                return false ;
                            }else{
                                if (!addLimitedGoods(limitedGoodsPOJO)) {
                                    logger.error("新增限量购失败:" + JSON.toJSONString(limitedGoodsPOJO));
                                }
                                limitedGoodsIds.add(limitedGoodsPOJO.getLimitedGoodsId());
                            }
                        }
                    }
                }
            }
            //根据新的排序对象确定是否有需要删除的限量购商品
            String oldLimitedGoodsSort = goodsSortMapper.selectGoodsPropertyValue(GoodsConstant.LIMITED_GOODS_KEY);
            String limitedGoodsSort = JSON.toJSONString(limitedGoodsIds);
            //判断是新增还是编辑排序
            if (oldLimitedGoodsSort != null && !"".equals(oldLimitedGoodsSort)) {
                List<Long> oldLimitedGoodsIds = JSON.parseArray(oldLimitedGoodsSort, Long.class);
                for (Long limitedGoodsId : oldLimitedGoodsIds) {
                    if (!limitedGoodsIds.contains(limitedGoodsId)) {
                        if (!deletedLimitedGoods(limitedGoodsId)) {
                            logger.error("id为:" + limitedGoodsId + "的限量购商品删除失败");
                        }
                    }
                }
                if (goodsSortMapper.updateGoodsPropertyValue(GoodsConstant.LIMITED_GOODS_KEY, limitedGoodsSort) != 1) {
                    logger.error("编辑限量购商品排序失败");
                    return false;
                }
            } else {
                if (goodsSortMapper.insertGoodsPropertyValue(GoodsConstant.LIMITED_GOODS_KEY, limitedGoodsSort) != 1) {
                    logger.error("保存限量购商品排序失败");
                    return false;
                }
            }
        } else {
            logger.error("限量购商品内容为空");
            return false;
        }
        return true;
    }

    @Override
    public Boolean addLimitedGoods(LimitedGoodsPOJO limitedGoodsPOJO) {
        if (validateLimited(limitedGoodsPOJO)) {
            limitedGoodsPOJO.setCreated(System.currentTimeMillis() / 1000);
            limitedGoodsPOJO.setUpdated(System.currentTimeMillis() / 1000);
            limitedGoodsPOJO.setLimitedRealStock(limitedGoodsPOJO.getLimitedStock());//限量购真实库存==总库存
            if (limitedGoodsMapper.insertLimitedGoods(limitedGoodsPOJO) == 1) {
                //减库存
                if (goodsService.updateStock(limitedGoodsPOJO.getLimitedStock(), limitedGoodsPOJO.getLimitedGoodsId())) {
                    return true;
                } else {
                    logger.error("id为:" + limitedGoodsPOJO.getLimitedGoodsId() + "的限量商品库存大于原商品的库存");
                }
            } else {
                logger.error("新增限时购商品失败");
            }
        } else {
            logger.error("新增限时购商品时参数有误:" + JSON.toJSONString(limitedGoodsPOJO));
        }
        return false;
    }


    @Override
    public Boolean modifyLimitedGoods(LimitedGoodsPOJO limitedGoodsPOJO) {
        if (validateLimited(limitedGoodsPOJO)) {
            limitedGoodsPOJO.setUpdated(System.currentTimeMillis() / 1000);
            LimitedGoodsPOJO oldLimitedGoodsPOJO = queryLimitedGoodsById(limitedGoodsPOJO.getLimitedGoodsId());
            if (oldLimitedGoodsPOJO != null) {
                Integer updatedStock = limitedGoodsPOJO.getLimitedStock() - oldLimitedGoodsPOJO.getLimitedStock();//计算库存变动
                if (goodsService.updateStock(updatedStock, limitedGoodsPOJO.getLimitedGoodsId())) {//修改商品库存
                    limitedGoodsPOJO.setLimitedRealStock(updatedStock);//修改实时库存
                    if (limitedGoodsMapper.updateLimitedGoods(limitedGoodsPOJO) == 1) {
                        return true;
                    } else {
                        logger.error("编辑限时购商品失败");
                    }
                } else {
                    logger.error("id为:" + limitedGoodsPOJO.getLimitedGoodsId() + "的限量商品库存大于原商品的库存");
                }
            } else {
                logger.error("id为:" + limitedGoodsPOJO.getLimitedGoodsId() + "的限量商品不存在");
            }
        } else {
            logger.error("编辑限时购商品时参数有误:" + JSON.toJSONString(limitedGoodsPOJO));
        }
        return false;
    }

    @Override
    public List<LimitedGoodsPOJO> queryLimitedGoodsList() {
        String limitedGoodsSort = goodsSortMapper.selectGoodsPropertyValue(GoodsConstant.LIMITED_GOODS_KEY);
        List<LimitedGoodsPOJO> limitedGoodsPOJOs = null;
        if (limitedGoodsSort != null && !"".equals(limitedGoodsSort) && !"[]".equals(limitedGoodsSort)) {
            List<Long> ids = JSON.parseArray(limitedGoodsSort, Long.class);
            //查询排序的商品基本属性
            List<GoodsPOJO> goodsPOJOs = goodsService.queryGoodsBasesByGoodsIds(ids);
            //查询排序商品的限量购属性
            limitedGoodsPOJOs = limitedGoodsMapper.selectLimitedGoodsList(ids);
            //匹配基本属性和限量购属性--取交集
            if(limitedGoodsPOJOs != null && limitedGoodsPOJOs.size()>0 && goodsPOJOs != null && goodsPOJOs.size() > 0){
                for(GoodsPOJO goodsPOJO : goodsPOJOs){
                    for(LimitedGoodsPOJO limitedGoodsPOJO : limitedGoodsPOJOs){
                        if(goodsPOJO.getGoodsId().equals(limitedGoodsPOJO.getLimitedGoodsId())){
                            limitedGoodsPOJO.setGoodsTitle(goodsPOJO.getTitle());
                            //计算库存上限
                            limitedGoodsPOJO.setGoodsLeaveStock(goodsPOJO.getStockNum()- goodsPOJO.getSaleNum()+limitedGoodsPOJO.getLimitedRealStock());
                            limitedGoodsPOJO.setShopPrice(goodsPOJO.getShopPrice());
                        }
                    }
                }
                if(limitedGoodsPOJOs.size() > 0){
                    limitedGoodsPOJOs = GoodsSortUtils.getSortedObject(limitedGoodsPOJOs, ids);
                }else{
                    logger.error("无有效的限量购排序商品");
                }
            }
        } else {
            logger.warn("未查询到限量购商品的排序");
        }
        return limitedGoodsPOJOs;
    }

    @Override
    public Boolean deletedLimitedGoods(Long limitedGoodsId) {
        if (limitedGoodsId != null) {
            LimitedGoodsPOJO limitedGoodsPOJO = queryLimitedGoodsById(limitedGoodsId);
            if (limitedGoodsPOJO != null) {
                if (limitedGoodsMapper.deletedLimitedGoods(limitedGoodsId) == 1) {
                    //删除成功返还库存"-"
                    if (goodsService.updateStock(-limitedGoodsPOJO.getLimitedRealStock(), limitedGoodsId)) {
                        return true;
                    } else {
                        logger.error("删除限量购商品返还库存失败");
                    }
                } else {
                    logger.error("删除限量购商品失败");
                }
            } else {
                logger.error("id为:" + limitedGoodsId + "的限量商品不存在");
            }
        } else {
            logger.error("删除限量购商品时Id为空");
        }
        return false;
    }

    @Override
    public LimitedGoodsPOJO queryLimitedGoodsById(Long goodsId) {
        if (goodsId == null || goodsId == 0) {
            logger.error("根据商品id查询活动商品信息时goodsId为空");
            return null;
        }
        return limitedGoodsMapper.selectLimitedGoodsByLimitedGoodsId(goodsId);
    }


    /**
     * 校验限量购实体
     *
     * @param limitedGoodsPOJO 限量购对象
     * @return 是否通过校验
     */
    private boolean validateLimited(LimitedGoodsPOJO limitedGoodsPOJO) {
        if (limitedGoodsPOJO != null) {
            Long id = limitedGoodsPOJO.getLimitedGoodsId();
            Integer limitedStock = limitedGoodsPOJO.getLimitedStock();
            String limitedPrice = limitedGoodsPOJO.getLimitedPrice();
            if (id == null) {
                logger.error("id为空");
                return false;
            }
            if (limitedStock == null) {
                logger.error("限量库存为空");
                return false;
            }
            if (limitedPrice == null || "".equals(limitedPrice)) {
                logger.error("限量购价格为空");
                return false;
            }
        } else {
            logger.error("限量购对象为空");
            return false;
        }
        return true;
    }

}

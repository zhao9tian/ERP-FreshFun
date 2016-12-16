package com.quxin.freshfun.service.goods.impl;

import com.alibaba.fastjson.JSON;
import com.quxin.freshfun.constant.GoodsConstant;
import com.quxin.freshfun.dao.GoodsSortMapper;
import com.quxin.freshfun.dao.LimitedGoodsMapper;
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
        if (limitedGoodsPOJOs != null && limitedGoodsPOJOs.size() > 0) {
            if(limitedGoodsPOJOs.size() != new HashSet<>(limitedGoodsPOJOs).size()){
                logger.error("限量购商品有重复");
                return false ;
            }
            List<Long> limitedGoodsIds = new ArrayList<>();
            for (LimitedGoodsPOJO limitedGoodsPOJO : limitedGoodsPOJOs) {
                //根据Id判断是否修改或新增
                if(goodsService.queryGoodsBaseByGoodsId(limitedGoodsPOJO.getLimitedGoodsId()) == null){
                    logger.error("商品Id为:"+limitedGoodsPOJO.getLimitedGoodsId()+"不存在");
                    return false ;
                }
                if (queryLimitedGoodsById(limitedGoodsPOJO.getLimitedGoodsId()) != null) {
                    if (!modifyLimitedGoods(limitedGoodsPOJO)) {
                        logger.error("编辑限量购失败:" + JSON.toJSONString(limitedGoodsPOJO));
                    }
                    limitedGoodsIds.add(limitedGoodsPOJO.getLimitedGoodsId());
                } else {
                    if (!addLimitedGoods(limitedGoodsPOJO)) {
                        logger.error("新增限量购失败:" + JSON.toJSONString(limitedGoodsPOJO));
                    }
                    limitedGoodsIds.add(limitedGoodsPOJO.getLimitedGoodsId());
                }
            }
            //根据新的排序对象确定是否有需要删除的限量购商品
            String oldlimitedGoodsSort = goodsSortMapper.selectGoodsPropertyValue(GoodsConstant.LIMITED_GOODS_KEY);
            String limitedGoodsSort = JSON.toJSONString(limitedGoodsIds);
            //判断是新增还是编辑排序
            if (oldlimitedGoodsSort != null && !"".equals(oldlimitedGoodsSort)) {
                List<Long> oldlimitedGoodsIds = JSON.parseArray(oldlimitedGoodsSort , Long.class);
                for(Long limitedGoodsId : oldlimitedGoodsIds){
                    if(!limitedGoodsIds.contains(limitedGoodsId)){
                        deletedLimitedGoods(limitedGoodsId);
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
            limitedGoodsPOJO.setCreated(System.currentTimeMillis()/1000);
            limitedGoodsPOJO.setUpdated(System.currentTimeMillis()/1000);
            if (limitedGoodsMapper.insertLimitedGoods(limitedGoodsPOJO) == 1) {
                return true;
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
            limitedGoodsPOJO.setUpdated(System.currentTimeMillis()/1000);
            if (limitedGoodsMapper.updateLimitedGoods(limitedGoodsPOJO) == 1) {
                return true;
            } else {
                logger.error("编辑限时购商品失败");
            }
        } else {
            logger.error("编辑限时购商品时参数有误:" + JSON.toJSONString(limitedGoodsPOJO));
        }
        return false;
    }

    @Override
    public List<LimitedGoodsPOJO> queryLimitedGoodsList() {
        String limitedGoodsSort = goodsSortMapper.selectGoodsPropertyValue(GoodsConstant.LIMITED_GOODS_KEY);
        List<LimitedGoodsPOJO> limitedGoodsSorts = null;
        if(limitedGoodsSort != null && !"".equals(limitedGoodsSort)){
            List<Long> ids = JSON.parseArray(limitedGoodsSort , Long.class);
            limitedGoodsSorts = GoodsSortUtils.getSortedObject(limitedGoodsMapper.selectLimitedGoodsList(ids) , ids);
        }else{
            logger.warn("未查询到限量购商品的排序");
        }
        return limitedGoodsSorts;
    }

    @Override
    public Boolean deletedLimitedGoods(Long limitedGoodsId) {
        if (limitedGoodsId != null) {
            if (limitedGoodsMapper.deletedLimitedGoods(limitedGoodsId) == 1) {
                return true;
            } else {
                logger.error("删除限量购商品失败");
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

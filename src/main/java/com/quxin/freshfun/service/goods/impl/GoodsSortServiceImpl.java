package com.quxin.freshfun.service.goods.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.quxin.freshfun.constant.GoodsConstant;
import com.quxin.freshfun.dao.GoodsSortMapper;
import com.quxin.freshfun.model.goods.GoodsPOJO;
import com.quxin.freshfun.model.goods.GoodsSortPOJO;
import com.quxin.freshfun.service.goods.GoodsService;
import com.quxin.freshfun.service.goods.GoodsSortService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 排序Service实现类
 * Created by qucheng on 2016/10/12.
 */
@Service("goodsSortService")
public class GoodsSortServiceImpl implements GoodsSortService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private GoodsSortMapper goodsSortMapper;

    @Autowired
    private GoodsService goodsService;

    @Override
    public List<GoodsPOJO> querySortGoods() {
        String sortValue = goodsSortMapper.selectPictureWall(GoodsConstant.PICTURE_WALL);
        if (sortValue == null || "".equals(sortValue)) {
            return null;
        }
        JSONArray sortArr = JSON.parseArray(sortValue);
        List<GoodsPOJO> sortList = new ArrayList<>();
        if (sortArr != null && sortArr.size() > 0) {
            for (Object goodsId : sortArr) {
                Integer goodsIdint = (Integer) goodsId;
                Long goodsLong = (long)goodsIdint;
                GoodsPOJO goods = goodsService.queryGoodsByGoodsId(goodsLong);
                if(goods == null){
                    logger.error("商品Id为:"+goodsId+"的商品不存在或者已经下架");
                }else{
                    sortList.add(goods);
                }
            }
            return sortList;
        } else {
            return null;
        }
    }

    @Override
    public GoodsPOJO querySortGoodsById(Long goodId) {
        if (goodId == null) {
            logger.error("goodsId不能为空");
            return null;
        }
        return goodsService.queryGoodsByGoodsId(goodId);
    }

    @Override
    public Boolean addAllGoodsSort(List<Integer> allSort) {
        Boolean b = false;
        if (allSort != null && allSort.size() > 0) {
            Integer[] sort = new Integer[allSort.size()];
            if (allSort.size() > 20) {
                logger.error("排序对象总数大于20");
            } else {
                for (int i = 0; i < allSort.size(); i++) {
                    sort[i] = allSort.get(i);
                }
                GoodsSortPOJO goodsSortPOJO = new GoodsSortPOJO();
                goodsSortPOJO.setSortKey(GoodsConstant.PICTURE_WALL);
                goodsSortPOJO.setSortValue(JSON.toJSONString(sort));
                //判断是插入还是编辑
                String sortValue = goodsSortMapper.selectPictureWall(GoodsConstant.PICTURE_WALL);
                if (sortValue != null && !"".equals(sortValue)) {
                    //编辑
                    Integer num = goodsSortMapper.updateGoodsSort(goodsSortPOJO);
                    if (num == 0) {
                        logger.error("插入排序对象失败");
                        return false;
                    }
                } else {
                    //插入
                    Integer num = goodsSortMapper.insertGoodsSort(goodsSortPOJO);
                    if (num == 0) {
                        logger.error("插入排序对象失败");
                        return false;
                    }
                }
                b = true;
            }
        } else {
            logger.error("排序对象为空");
        }
        return b;
    }

}

package com.quxin.freshfun.utils;

import com.quxin.freshfun.model.goods.LimitedGoodsPOJO;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于查询商品排序的工具类
 * Created by qucheng on 16/12/15.
 */
public class GoodsSortUtils {


    /**
     * 获取限量购排序对象
     * @param unSortedObject 未排序的对象
     * @param sortRule 排序规则
     * @return 排序了的对象
     */
    public static List<LimitedGoodsPOJO> getSortedObject(List<LimitedGoodsPOJO> unSortedObject , List<Long> sortRule){
        List<LimitedGoodsPOJO> sortedObject = null;
        if(sortRule != null){
            sortedObject = new ArrayList<>();
            for(Long id : sortRule){
                for(LimitedGoodsPOJO goods : unSortedObject){
                    if(id.equals(goods.getLimitedGoodsId())){
                        sortedObject.add(goods);
                    }
                }
            }
        }
        return sortedObject;
    }
}

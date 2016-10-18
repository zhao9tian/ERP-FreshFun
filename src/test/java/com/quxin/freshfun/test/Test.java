package com.quxin.freshfun.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.quxin.freshfun.model.goods.GoodsSortParam;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by qucheng on 2016/10/17.
 */
public class Test {
    public static void main(String[] args) {
        List<GoodsSortParam> allSort = new ArrayList<>();
        GoodsSortParam g1 = new GoodsSortParam();
        g1.setSortId(111);
        g1.setGoodsId(222);
        allSort.add(g1);
        GoodsSortParam g2 = new GoodsSortParam();
        g2.setSortId(111);
        g2.setGoodsId(222);
        allSort.add(g2);
        System.out.println(allSort);


        Integer[] sort = new Integer[20];
        for(int i = 0 ; i < allSort.size() ; i++){
            sort[i] = allSort.get(i).getGoodsId();
        }
        JSONArray sortArr = JSON.parseArray(JSON.toJSONString(sort));
        System.out.println(sortArr);
//        System.out.println(JSON.toJSONString(sort));
    }
}

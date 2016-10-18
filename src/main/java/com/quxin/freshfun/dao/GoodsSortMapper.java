package com.quxin.freshfun.dao;

import com.quxin.freshfun.model.goods.GoodsPOJO;
import com.quxin.freshfun.model.goods.GoodsSortPOJO;
import com.quxin.freshfun.model.goods.GoodsSortParam;

import java.util.List;

/**
 * dao层数据
 * Created by qucheng on 2016/10/12.
 */
public interface GoodsSortMapper {

    /**
     * 查询排序商品
     * @return 返回排序商品
     * @param sortList
     */
    List<GoodsPOJO> selectSortGoods(List<Integer> sortList);

    /**
     * 根据商品Id查询商品基本信息
     * @param goodId
     * @return
     */
    GoodsPOJO selectGoodsPOJOById(Integer goodId);

    /***
     * 插入排序对象 其实是修改
     * @param goodsSortPOJO 排序对象
     * @return 返回插入条数
     */
    Integer insertGoodsSort(GoodsSortPOJO goodsSortPOJO);

    /**
     * 查询图墙里面是否有信息
     * @param sortKey 排序类型
     * @return 返回排序结果
     */
    String selectPictureWall(String sortKey);

    /**
     * 修改排序内容
     * @param goodsSortPOJO 排序对象
     * @return 返回0或1
     */
    Integer updateGoodsSort(GoodsSortPOJO goodsSortPOJO);

}

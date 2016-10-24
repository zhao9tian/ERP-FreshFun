package com.quxin.freshfun.dao;

import com.quxin.freshfun.model.goods.GoodsSortPOJO;

/**
 * dao层数据
 * Created by qucheng on 2016/10/12.
 */
public interface GoodsSortMapper {


    /***
     * 插入排序对象 其实是修改
     * @param goodsSortPOJO 排序对象
     * @return 返回插入条数
     */
    Integer insertGoodsSort(GoodsSortPOJO goodsSortPOJO);

    /**
     * 查询图墙里面是否有信息
     *
     * @param sortKey 排序类型
     * @return 返回排序结果
     */
    String selectPictureWall(String sortKey);

    /**
     * 修改排序内容
     *
     * @param goodsSortPOJO 排序对象
     * @return 返回0或1
     */
    Integer updateGoodsSort(GoodsSortPOJO goodsSortPOJO);

}

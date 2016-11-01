package com.quxin.freshfun.dao;

import com.quxin.freshfun.model.goods.GoodsSortPOJO;
import org.apache.ibatis.annotations.Param;

/**
 * dao层数据
 * Created by qucheng on 2016/10/12.
 */
public interface GoodsSortMapper {


    /***
     * 插入排序对象 其实是修改
     * @param key key属性
     * @param value value值
     * @return 返回插入条数
     */
    Integer insertGoodsSort(@Param("propertyKey") String key , @Param("propertyValue")String value);

    /**
     * 查询图墙里面是否有信息
     *
     * @param sortKey 排序类型
     * @return 返回排序结果
     */
    String selectPictureWall(String sortKey);

    /**
     * 修改排序内容
     * @param key key属性
     * @param value value值
     * @return 返回0或1
     */
    Integer updateGoodsSort(@Param("propertyKey") String key , @Param("propertyValue")String value);





    /**
     * 根据key查询属性表里面的value
     * @param key key属性
     * @return value值
     */
    String selectGoodsPropertyValue(String key);

    /**
     * 添加商品属性
     * @param key key属性
     * @param value value值
     * @return 返回记录数
     */
    Integer insertGoodsPropertyValue(@Param("propertyKey") String key , @Param("propertyValue")String value);

    /**
     * 编辑商品属性
     * @param key key属性
     * @param value value值
     * @return 返回记录数
     */
    Integer updateGoodsPropertyValue(@Param("propertyKey") String key , @Param("propertyValue")String value);
}

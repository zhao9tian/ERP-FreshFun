package com.quxin.freshfun.dao;

import com.quxin.freshfun.model.goods.GoodsPOJO;
import com.quxin.freshfun.model.goods.GoodsStandardPOJO;

import java.util.List;
import java.util.Map;

/**
 * 商品dao层
 */
public interface GoodsMapper {

    /**
     * 删除商品 修改is_deleted
     * @param goodsId 商品id
     * @return 删除记录数
     */
    Integer deleteGoodsByGoodsId(Long goodsId);

    /**
     * 根据商品Id查询商品详情
     * @param goodsId 商品Id
     * @return 返回商品信息
     */
    GoodsPOJO selectGoodsByGoodsId(Long goodsId);

    /**
     * 查询商品图片信息
     * @param goodsId 商品Id
     * @return 商品图片信息
     */
    GoodsPOJO selectGoodsImgByGoodsId(Long goodsId);

    /**
     * 根据商品Id查询商品规格
     * @param goodsId 商品规格
     * @return 返回规格实体
     */
    GoodsStandardPOJO selectGoodsStandardByGoodsId(Long goodsId);

    /**
     * 校验商品名称重复
     * @param title 商品名称
     * @return 数量
     */
    Integer selectCountByGoodsName(String title);


    /**
     * 插入商品基本信息
     * @param goodsPOJO 商品所有信息 ,只保存基本信息
     * @return 返回插入记录数
     */
    Integer insertGoodsBase(GoodsPOJO goodsPOJO);

    /**
     * 插入商品图片信息
     * @param goodsPOJO 商品所有信息 ,只保存图片信息
     * @return 返回插入记录数
     */
    Integer insertGoodsImg(GoodsPOJO goodsPOJO);

    /**
     * 插入商品规格信息
     * @param goodsStandardPOJO 商品规格属性
     * @return 返回插入记录数
     */
    Integer insertGoodsStandard(GoodsStandardPOJO goodsStandardPOJO);

    /**
     * 修改商品
     * @param goodsPOJO 商品信息
     * @return 修改条数
     */
    Integer updateGoods(GoodsPOJO goodsPOJO);

    /**
     * 编辑商品图片信息
     * @param goodsPOJO 商品图片信息
     * @return 修改记录数
     */
    Integer updateGoodsImg(GoodsPOJO goodsPOJO);

    /**
     * 修改规格
     * @param goodsStandardPOJO 规格信息
     * @return 修改规格记录数
     */
    Integer updateGoodsStandard(GoodsStandardPOJO goodsStandardPOJO);

    /**
     * 查询记录总数
     * @param queryCondition 查询条件
     * @return 返回满足查询条件的记录总数
     */
    Integer selectCountByQC(Map<String, Object> queryCondition);

    /**
     * 查询商品列表
     * @param queryCondition 查询条件
     * @return 商品列表
     */
    List<GoodsPOJO> selectGoodsList(Map<String, Object> queryCondition);

}
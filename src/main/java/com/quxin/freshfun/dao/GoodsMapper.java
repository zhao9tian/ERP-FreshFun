package com.quxin.freshfun.dao;

import com.quxin.freshfun.model.goods.GoodsCategoryOut;

import java.util.List;

/**
 * 商品dao层
 */
public interface GoodsMapper {

	/**
	 * 查询分类信息列表
	 * @return 返回类目列表
	 */
	List<GoodsCategoryOut> selectCategoryList();
}
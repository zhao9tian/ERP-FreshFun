package com.quxin.freshfun.dao;

import java.util.List;
import java.util.Map;

import com.quxin.freshfun.model.goods.GoodsPOJO;

/**
 * 商品Dao层
 * @author TuZl
 * @time 2016年8月22日下午9:09:17
 */
public interface GoodsMapper {

	/**
	 * 返回商品列表
	 * @return
	 */
	List<GoodsPOJO> findAll(Map<String, Object> map);
	
	/**
	 * 返回复合查询条件的数据总数
	 * @param map
	 * @return
	 */
	Integer total(Map<String, Object> map);
	
	/**
	 * 保存商品信息
	 * @param goods
	 * @return 返回商品Id
	 */
	Integer save(GoodsPOJO goods);
	
	/**
	 * 修改商品信息
	 * @param goods
	 */
	void update(GoodsPOJO goods);
	

	/**
	 * 修改商品状态为下架
	 * @param id
	 */
	void removeGood(Integer id);

	/**
	 * 获取商品信息
	 * @param goodsId 
	 * @return
	 */
	GoodsPOJO getGoodsById(Integer goodsId);
	
	/**
	 * 获取商品信息
	 * @param goodsId 
	 * @return
	 */
	GoodsPOJO selectGoodsById(Integer goodsId);
	
	
	/**
	 * 根据商品Id获取商品类型id
	 * @param goodsId
	 * @return
	 */
	List<Integer> getGoodsTypeIdsByGoodsId(Integer goodsId);

	/**
	 * 删除关联关系
	 * @param id
	 */
	void deleteTypes(Integer id);
	
	/**
	 * 获取名称数量用于验证
	 * @param map
	 * @return
	 */
	Integer getTotalGoodsName(Map<String, Object> map);
	
	/**
	 * 根据专题id查询专题商品信息
	 * @param map
	 * @return
	 */
	List<GoodsPOJO> findSpecialGoods(Map<String,Object> map);
	
	/**
	 * 根据Banner id查询专题商品信息
	 * @param map
	 * @return
	 */
	List<GoodsPOJO> findSpecialMallGoods(Map<String,Object> map);
	
	/**
	 * 根据分类 id查询分类商品信息
	 * @param map
	 * @return
	 */
	List<GoodsPOJO> findTypeGoods(Map<String,Object> map);
	/**
	 * 根据活动 id查询活动商品信息
	 * @param map
	 * @return
	 */
	List<GoodsPOJO> findActivityGoods(Map<String,Object> map);
	
	
	/**
	 * 根据代理商户ID查询代理的商品信息
	 * 
	 * @param map
	 * @return
	 */
	List<GoodsPOJO> findProxyGoods(Map<String,Object> map);
	
	/**
	 * 根据订单id查询上月销量
	 * @param goodsId
	 * @return
	 */
	Integer getLastSales(Integer goodsId);
	/**
	 * 根据订单id查询总销量
	 * @param goodsId
	 * @return
	 */
	Integer getAllSales(Integer goodsId);

	void grounding(Map<String,Object> map);

	/**
	 * 新增精选商品
	 * @param map
	 * @return
	 */
	Integer insertGoodsSelection(Map<String, Object> map);

	/**
	 * 查询B端商品
	 * ziming -16.9.28
	 * @param map 参数，开始数begin和每页数据量pageSize
	 * @return
	 */
	List<GoodsPOJO> selectGoodsByAgent(Map<String, Object> map);

	/**
	 * c端往b端推商品
	 * ziming -16.9.28
	 * @param id
	 * @return
	 */
	Integer updateGoodsAgentWithC(Integer id);

	/**
	 * b端上下架商品
	 * ziming -16.9.28
	 * @param map
	 * @return
	 */
	Integer updateGoodsAgentWithB(Map<String, Object> map);

	/**
	 * b端绑定代理人
	 * ziming -16.9.28
	 * @param map    goodsId    商品Id,userId    代理人id
	 * @return
	 */
	Integer updateGoodsWithAgent(Map<String, Object> map);
}
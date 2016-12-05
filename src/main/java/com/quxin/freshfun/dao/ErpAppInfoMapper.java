package com.quxin.freshfun.dao;

import com.quxin.freshfun.model.erpuser.ErpAppInfoPOJO;
import com.quxin.freshfun.model.outparam.AppInfoOutParam;

import java.util.List;
import java.util.Map;

/**
 * Created by Ziming on 2016/11/7.
 */
public interface ErpAppInfoMapper {
    /**
     * 插入平台信息
     * @param appInfo 平台信息
     * @return 受影响行数
     */
    Integer insertErpAppInfo(ErpAppInfoPOJO appInfo);

    /**
     * 根据id更新appId
     * @param id id
     * @return 受影响行数
     */
    Integer updateErpAppIdById(Long id);

    /**
     * 查询平台列表
     * @return 平台信息列表
     */
    List<AppInfoOutParam> selectErpAppInfo(Map<String,Object> map);

    /**
     * 根据条件查询app总条数
     * @param map appName 模糊查询的app名称
     * @return 总条数
     */
    Integer selectAppCount(Map<String ,Object> map);

    /**
     * 更新平台信息
     * @param appInfo 平台信息
     * @return 受影响行数
     */
    Integer updateErpAppInfo(ErpAppInfoPOJO appInfo);

    /**
     * 删除平台信息
     * @param map 参数 appId  平台id，updated  更新时间
     * @return 受影响行数
     */
    Integer deleteErpAppInfo(Map<String,Object> map);

    /**
     * 根据appId获取商城信息
     * @param appId 商城id
     * @return 商城信息
     */
    ErpAppInfoPOJO selectAppById(Long appId);
    /**
     * 根据appName获取商城信息
     * @param map  appName 商城名称
     * @return 商城信息
     */
    List<AppInfoOutParam> selectAppsByName(Map<String,Object> map);

    /**
     * 根据appName获取商城信息
     * @param appName 商城名称
     * @return 商城信息
     */
    List<AppInfoOutParam> selectAppByName(String appName);
}

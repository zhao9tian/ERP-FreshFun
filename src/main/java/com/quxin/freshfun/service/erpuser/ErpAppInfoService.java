package com.quxin.freshfun.service.erpuser;

import com.quxin.freshfun.model.erpuser.ErpAppInfoPOJO;
import com.quxin.freshfun.model.outparam.AppInfoOutParam;

import java.util.List;

/**
 * Created by Ziming on 2016/11/7.
 */
public interface ErpAppInfoService {
    /**
     * 插入平台信息
     * @param appName 平台名称
     * @return appId
     */
    Long addErpAppInfo( String appName);

    /**
     * 查询平台列表
     * @return 平台信息列表
     */
    List<AppInfoOutParam> queryErpAppInfo();

    /**
     * 更新平台信息
     * @param appId 平台id
     * @param appName 平台名称
     * @return 受影响行数
     */
    Integer modifyErpAppInfo(Long appId,String appName);

    /**
     * 删除平台信息
     * @param appId  平台id
     * @return 受影响行数
     */
    Integer removeAppInfo(Long appId);

    /**
     * 根据appId获取商城信息
     * @param appId 商城id
     * @return 商城信息
     */
    ErpAppInfoPOJO queryAppById(Long appId);
    /**
     * 根据商城名称获取商城信息
     * @param appName 商城名称
     * @return 商城信息
     */
    List<AppInfoOutParam> queryAppByName(String appName);
}

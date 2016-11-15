package com.quxin.freshfun.service.erpuser;

import com.quxin.freshfun.model.erpuser.ErpAppInfoPOJO;

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
    List<ErpAppInfoPOJO> queryErpAppInfo();

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
}

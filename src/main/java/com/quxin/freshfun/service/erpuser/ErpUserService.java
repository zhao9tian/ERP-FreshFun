package com.quxin.freshfun.service.erpuser;

import com.quxin.freshfun.model.erpuser.ErpUserPOJO;

import java.util.List;

/**
 * Created by Ziming on 2016/10/28.
 */
public interface ErpUserService {

    Integer addErpUser(ErpUserPOJO erpUser);

    Integer modifyErpUser(ErpUserPOJO erpUser);

    /**
     * 根据appId查询后台用户
     * @param appId 平台appId
     * @return 用户列表
     */
    List<ErpUserPOJO> queryErpUserByAppId(String appId);

    ErpUserPOJO erpUserLogin(String account);
}

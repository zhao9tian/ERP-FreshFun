package com.quxin.freshfun.service.erpuser;

import com.quxin.freshfun.model.erpuser.ErpUserPOJO;

import java.util.List;

/**
 * Created by Ziming on 2016/10/28.
 */
public interface ErpUserService {

    /**
     * 新增用户
     * @param erpUser 用户对象
     * @return 受影响行数
     */
    Integer addErpUser(ErpUserPOJO erpUser);

    /**
     * 修改用户信息
     * @param erpUser 用户对象
     * @return 受影响行数
     */
    Integer modifyErpUser(ErpUserPOJO erpUser);

    /**
     * 根据appId查询后台用户
     * @param appId 平台appId
     * @return 用户列表
     */
    List<ErpUserPOJO> queryErpUserByAppId(String appId);

    /**
     * 用户登录查询
     * @param account 帐号
     * @return 用户信息
     */
    ErpUserPOJO erpUserLogin(String account);
}

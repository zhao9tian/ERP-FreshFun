package com.quxin.freshfun.dao;

import com.quxin.freshfun.model.erpuser.ErpUserPOJO;

import java.util.List;
import java.util.Map;

/**
 * Created by Ziming on 2016/10/27.
 */
public interface ErpUserMapper {

    /**
     * 插入后台用户信息
     * @param user 后台用户信息
     * @return 受影响行数
     */
    Integer insertErpUser(ErpUserPOJO user);

    /**
     * 更新后台用户id
     * @param id id
     * @return 受影响行数
     */
    Integer updateErpUserIdById(Long id);

    /**
     * 更新后台用户信息
     * @param map 参数map
     * @return 受影响行数
     */
    Integer updateErpUserByUserId(Map<String,Object> map);

    /**
     * 根据平台appId查询用户信息
     * @param userAppId 平台appId
     * @return 用户列表
     */
    List<ErpUserPOJO> selectErpUserByAppId(String userAppId);

    /**
     * 根据用户帐号查询用户信息
     * @param userAccount 用户帐号
     * @return 用户信息
     */
    ErpUserPOJO selectErpUserByAccount(String userAccount);

    /**
     * 根据用户id查询用户信息
     * @param userId 用户id
     * @return 用户信息
     */
    ErpUserPOJO selectErpUserByUserId(Long userId);
}

package com.quxin.freshfun.dao;

import com.quxin.freshfun.model.user.UserInfoOutParam;

import java.util.List;

/**
 * Created by qingtian on 2016/10/15.
 */
public interface UserBaseMapper {

    /**
     * 根据userId查询用户信息
     * @param userId  userId
     * @return 用户信息
     */
    UserInfoOutParam selectUserInfoByUserId(Long userId);

    /**
     * 根据公众号名称查询公众号编号
     * @param appName
     * @return
     */
    List<Long> selectAppIdByAppName(String appName);

    /**
     * 根据用户昵称查询用户编号
     * @param userName
     * @return
     */
    List<Long> selectUserIdByNickName(String userName);

    /**
     * 根据AppId查询公众号名称
     * @param appId
     * @return
     */
    String selectAppNameByAppId(Long appId);
}

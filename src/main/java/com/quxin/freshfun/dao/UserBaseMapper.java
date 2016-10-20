package com.quxin.freshfun.dao;

import com.quxin.freshfun.model.user.UserInfoOutParam;

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

}

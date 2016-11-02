package com.quxin.freshfun.service.erpuser.impl;

import com.quxin.freshfun.dao.ErpUserMapper;
import com.quxin.freshfun.model.erpuser.ErpUserPOJO;
import com.quxin.freshfun.service.erpuser.ErpUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ziming on 2016/10/28.
 */
@Service("erpUserService")
public class ErpUserServiceImpl implements ErpUserService{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ErpUserMapper erpUserMapper;

    /**
     * 新增用户
     * @param erpUser 用户对象
     * @return 受影响行数
     */
    @Override
    public Integer addErpUser(ErpUserPOJO erpUser) {
        Integer result = erpUserMapper.insertErpUser(erpUser);
        if(result==1){
            result = erpUserMapper.updateErpUserIdById(erpUser.getId());
        }
        return result;
    }

    /**
     * 修改用户信息
     * @param erpUser 用户对象
     * @return 受影响行数
     */
    @Override
    public Integer modifyErpUser(ErpUserPOJO erpUser) {
        Map<String, Object> map = new HashMap<String, Object>();
        Integer result = 0;
        if (erpUser != null && erpUser.getUserId() != null && erpUser.getUserId() != 0) {
            map.put("userId", erpUser.getUserId());
            map.put("updated", System.currentTimeMillis() / 1000);
            if (erpUser.getUserHeadImg() != null && !"".equals(erpUser.getUserHeadImg())) {
                map.put("userHeadImg", erpUser.getUserHeadImg());
            }
            if (erpUser.getUserName() != null && !"".equals(erpUser.getUserName())) {
                map.put("userName", erpUser.getUserName());
            }
            if (erpUser.getUserPassword() != null && !"".equals(erpUser.getUserPassword())) {
                map.put("userPassword", erpUser.getUserPassword());
            }
            if (erpUser.getUserPhoneNumber() != null) {
                map.put("userPhoneNumber", erpUser.getUserPhoneNumber());
            }
            if (erpUser.getUserEmail() != null) {
                map.put("userEmail", erpUser.getUserEmail());
            }
            result = erpUserMapper.updateErpUserByUserId(map);
        } else {
            logger.warn("更新后台用户信息，入参有误");
        }
        return result;
    }

    /**
     * 根据appId查询后台用户
     * @param appId 平台appId
     * @return 用户列表
     */
    @Override
    public List<ErpUserPOJO> queryErpUserByAppId(String appId) {
        if (appId == null || "".equals(appId)) {
            logger.warn("根据appId查询用户信息，入参有误");
            return null;
        }
        return erpUserMapper.selectErpUserByAppId(appId);
    }

    /**
     * 用户登录查询
     * @param account 帐号
     * @return 用户信息
     */
    @Override
    public ErpUserPOJO erpUserLogin(String account) {
        if(account==null||"".equals(account)){
            logger.warn("根据appId查询用户信息，入参有误");
            return null;
        }else{
            return erpUserMapper.selectErpUserByAccount(account);
        }
    }
}

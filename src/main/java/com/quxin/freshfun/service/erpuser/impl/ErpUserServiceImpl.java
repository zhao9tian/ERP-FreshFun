package com.quxin.freshfun.service.erpuser.impl;

import com.quxin.freshfun.dao.ErpUserMapper;
import com.quxin.freshfun.model.erpuser.ErpUserPOJO;
import com.quxin.freshfun.service.erpuser.ErpAppInfoService;
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
    @Autowired
    private ErpAppInfoService erpAppInfoService;

    /**
     * 新增用户
     * @param erpUser 用户对象
     * @return 受影响行数
     */
    @Override
    public Integer addErpUser(ErpUserPOJO erpUser) {
        if(erpUser!=null&&erpUser.getAppName()!=null&&!"".equals(erpUser.getAppName())){
            Long appId = erpAppInfoService.addErpAppInfo(erpUser.getAppName());
            if(appId!=null)
                erpUser.setAppId(appId);
            else
                return 0;
        }
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

            if (erpUser.getPassword() != null && !"".equals(erpUser.getPassword())) {
                map.put("password", erpUser.getPassword());
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

    /**
     * 根据id查询用户信息
     * @param userId 用户id
     * @return 用户信息
     */
    @Override
    public ErpUserPOJO queryUserById(Long userId) {
        if(userId==null||userId==0){
            logger.warn("根据appId查询用户信息，入参有误");
            return null;
        }else{
            return erpUserMapper.selectErpUserByUserId(userId);
        }
    }
}

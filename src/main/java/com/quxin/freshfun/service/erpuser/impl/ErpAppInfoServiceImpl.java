package com.quxin.freshfun.service.erpuser.impl;

import com.quxin.freshfun.dao.ErpAppInfoMapper;
import com.quxin.freshfun.model.erpuser.ErpAppInfoPOJO;
import com.quxin.freshfun.model.outparam.AppInfoOutParam;
import com.quxin.freshfun.service.erpuser.ErpAppInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Ziming on 2016/11/7.
 */
@Service("erpAppInfoService")
public class ErpAppInfoServiceImpl implements ErpAppInfoService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ErpAppInfoMapper erpAppInfoMapper;
    /**
     * 插入平台信息
     * @param appName 平台名称
     * @return appId
     */
    @Override
    public Long addErpAppInfo(String appName) {
        if(appName==null||"".equals(appName)){
            logger.warn("新增公司信息入参有误");
            return 0l;
        }
        Long id = erpAppInfoMapper.selectMaxId();
        int number = new Random().nextInt(100);
        ErpAppInfoPOJO appInfo = new ErpAppInfoPOJO();
        appInfo.setId(id+100+number);
        appInfo.setAppId(id+100+number);
        appInfo.setAppName(appName);
        appInfo.setCreated(System.currentTimeMillis()/1000);
        appInfo.setUpdated(System.currentTimeMillis()/1000);
        Integer result = 0;
        try{
            result = erpAppInfoMapper.insertErpAppInfo(appInfo);
        }catch (Exception e){
            logger.error("新增公众号信息异常");
            e.printStackTrace();
        }
        /*if(result!=1)
            logger.warn("新增公司信息方法执行失败");
        else{
            result = erpAppInfoMapper.updateErpAppIdById(appInfo.getId());
        }*/
        if(result!=1){
            logger.warn("新增公司信息方法执行失败");
            return null;
        }
        return appInfo.getId();
    }

    /**
     * 查询平台列表
     * @return 平台信息列表
     */
    @Override
    public List<AppInfoOutParam> queryErpAppInfo(Integer curPage,Integer pageSize) {
        Integer begin = (curPage-1)*pageSize;
        Map<String ,Object> map = new HashMap<String ,Object>();
        map.put("begin",begin);
        map.put("pageSize",pageSize);
        return erpAppInfoMapper.selectErpAppInfo(map);
    }

    @Override
    public Integer queryErpAppCount(String appName) {
        Map<String ,Object> map = new HashMap<String ,Object>();
        if(appName!=null){
            appName=appName+"%";
            map.put("appName",appName);
        }
        return erpAppInfoMapper.selectAppCount(map);
    }

    /**
     * 更新平台信息
     * @param appId 平台id
     * @param appName 平台名称
     * @return 受影响行数
     */
    @Override
    public Integer modifyErpAppInfo(Long appId,String appName) {
        if(appId==null||appId==0||appName==null||"".equals(appName)){
            logger.warn("修改公司信息入参有误");
            return 0;
        }
        ErpAppInfoPOJO appInfo = new ErpAppInfoPOJO();
        appInfo.setAppId(appId);
        appInfo.setAppName(appName);
        appInfo.setUpdated(System.currentTimeMillis()/1000);
        Integer result = erpAppInfoMapper.updateErpAppInfo(appInfo);
        if(result!=1)
            logger.warn("修改公司信息方法执行失败");
        return result;
    }

    /**
     * 删除平台信息
     * @param appId  平台id
     * @return 受影响行数
     */
    @Override
    public Integer removeAppInfo(Long appId) {
        if(appId == null ||appId==0){
            logger.warn("删除公司信息入参有误");
            return 0;
        }
        Map<String ,Object> map = new HashMap<String,Object>();
        map.put("appId",appId);
        map.put("updated",System.currentTimeMillis()/1000);
        Integer result = erpAppInfoMapper.deleteErpAppInfo(map);
        if(result!=1)
            logger.warn("修改公司信息方法执行失败");
        return result;
    }

    @Override
    public ErpAppInfoPOJO queryAppById(Long appId) {
        if(appId==null||appId==0){
            logger.warn("根据appId获取商城信息方法入参有误");
            return null;
        }
        return erpAppInfoMapper.selectAppById(appId);
    }

    @Override
    public List<AppInfoOutParam> queryAppsByName(String appName,Integer curPage,Integer pageSize) {
        if(appName==null||"".equals(appName)){
            logger.warn("根据商城名称获取商城信息方法入参有误");
            return null;
        }
        appName = appName+"%";
        Integer begin = (curPage-1)*pageSize;
        Map<String ,Object> map = new HashMap<String ,Object>();
        map.put("appName",appName);
        map.put("begin",begin);
        map.put("pageSize",pageSize);
        return erpAppInfoMapper.selectAppsByName(map);
    }

    @Override
    public ErpAppInfoPOJO queryAppByName(String appName) {
        if(appName==null||"".equals(appName)){
            logger.warn("根据商城名称获取商城信息方法入参有误");
            return null;
        }
        List<ErpAppInfoPOJO> list = erpAppInfoMapper.selectAppByName(appName);
        if(list!=null&&list.size()>0)
            return list.get(0);
        return null;
    }
}

package com.quxin.freshfun.dao;

import com.quxin.freshfun.model.erpuser.ErpUserJurisdictionPOJO;

import java.util.List;
import java.util.Map;

/**
 * Created by Ziming on 2016/10/27.
 */
public interface ErpUserJurisdictionMapper {

    /**
     * 插入用户权限
     * @param erpUserJurisdiction 用户权限信息对象
     * @return 受影响行数
     */
    Integer insertErpUserJuris(ErpUserJurisdictionPOJO erpUserJurisdiction);

    /**
     * 根据userId查询用户权限
     * @param userId 用户id
     * @return 用户权限集合
     */
    List<ErpUserJurisdictionPOJO> selectErpUserJurisByUserId(Long userId);

    /**
     * 更新用户操作权限
     * @param map 参数map，id：主键id，operation：操作权限
     * @return 受影响行数
     */
    Integer updateOperationById(Map<String,Object> map);

    /**
     * 删除用户权限（逻辑）
     * @param id 主键id
     * @return 受影响行数
     */
    Integer deleteErpUserJurisById(Long id);
}

package com.quxin.freshfun.dao;

import com.quxin.freshfun.model.erpuser.ErpRolePOJO;

import java.util.List;

/**
 * Created by Ziming on 2016/10/27.
 */
public interface ErpRoleMapper {
    /**
     * 插入角色信息
     * @param role 角色信息
     * @return 受影响行数
     */
    Integer insertErpRole(ErpRolePOJO role);

    /**
     * 更新roleId
     * @param id id
     * @return 受影响行数
     */
    Integer updateErpRoleIdById(Long id);

    /**
     * 查询角色列表
     * @return  角色列表
     */
    List<ErpRolePOJO> selectErpRole();
}

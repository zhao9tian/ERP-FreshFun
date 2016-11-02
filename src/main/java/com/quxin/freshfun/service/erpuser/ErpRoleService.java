package com.quxin.freshfun.service.erpuser;

import com.quxin.freshfun.model.erpuser.ErpRolePOJO;

import java.util.List;

/**
 * Created by Ziming on 2016/10/28.
 */
public interface ErpRoleService {
    /**
     * 新增角色信息
     * @param erpRole 角色信息
     * @return 受影响行数
     */
    Integer addErpRole(ErpRolePOJO erpRole);

    /**
     * 查询角色列表
     * @return 角色列表
     */
    List<ErpRolePOJO> queryErpRole();
}

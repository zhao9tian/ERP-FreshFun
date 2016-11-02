package com.quxin.freshfun.service.erpuser;

import com.quxin.freshfun.model.erpuser.ErpRolePOJO;

import java.util.List;

/**
 * Created by Ziming on 2016/10/28.
 */
public interface ErpRoleService {

    Integer addErpRole(ErpRolePOJO erpRole);

    List<ErpRolePOJO> queryErpMenu();
}

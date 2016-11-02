package com.quxin.freshfun.service.erpuser.impl;

import com.quxin.freshfun.dao.ErpRoleMapper;
import com.quxin.freshfun.model.erpuser.ErpRolePOJO;
import com.quxin.freshfun.service.erpuser.ErpRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Ziming on 2016/10/28.
 */
@Service("erpRoleService")
public class ErpRoleServiceImpl implements ErpRoleService{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ErpRoleMapper erpRoleMapper;
    @Override
    public Integer addErpRole(ErpRolePOJO erpRole) {
        Integer result = erpRoleMapper.insertErpRole(erpRole);
        if(result==1){
            result = erpRoleMapper.updateErpRoleIdById(erpRole.getId());
        }
        return result;
    }

    @Override
    public List<ErpRolePOJO> queryErpMenu() {
        return erpRoleMapper.selectErpRole();
    }
}

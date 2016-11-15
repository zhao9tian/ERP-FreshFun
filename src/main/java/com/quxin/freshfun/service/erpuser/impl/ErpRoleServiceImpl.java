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
    /**
     * 新增角色信息
     * @param erpRole 角色信息
     * @return 受影响行数
     */
    @Override
    public Integer addErpRole(ErpRolePOJO erpRole) {
        if(erpRole==null){
            logger.warn("新增角色信息，入参有误");
            return 0;
        }
        erpRole.setUpdated(System.currentTimeMillis()/1000);
        erpRole.setCreated(System.currentTimeMillis()/1000);
        Integer result = erpRoleMapper.insertErpRole(erpRole);
        if(result==1){
            result = erpRoleMapper.updateErpRoleIdById(erpRole.getId());
        }
        return result;
    }

    /**
     * 查询角色列表
     * @return 角色列表
     */
    @Override
    public List<ErpRolePOJO> queryErpRole() {
        return erpRoleMapper.selectErpRole();
    }

    /**
     * 修改角色信息
     * @param role 角色信息
     * @return 受影响行数
     */
    @Override
    public Integer modifyErpRoleByRoleId(ErpRolePOJO role) {
        if(role==null||role.getRoleId()==null||role.getRoleId()==0){
            logger.warn("修改角色信息时，入参有误！");
            return 0;
        }
        role.setUpdated(System.currentTimeMillis()/1000);
        Integer result = erpRoleMapper.updateErpRoleByRoleId(role);
        if(result==0){
            logger.warn("修改角色信息时，受影响行数为0");
        }
        return result;
    }


}

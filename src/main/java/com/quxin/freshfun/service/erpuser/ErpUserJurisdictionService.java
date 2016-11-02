package com.quxin.freshfun.service.erpuser;

import com.quxin.freshfun.model.erpuser.ErpRolePOJO;
import com.quxin.freshfun.model.erpuser.ErpUserJurisdictionPOJO;

import java.util.List;

/**
 * Created by Ziming on 2016/10/28.
 */
public interface ErpUserJurisdictionService {
    /**
     * 新增授权信息
     * @param erpUserJuris 授权信息
     * @return 受影响行数
     */
    Integer addErpUserJuris(ErpUserJurisdictionPOJO erpUserJuris);

    /**
     * 根据用户id查询用户角色
     * @param userId 用户id
     * @return 授权信息集合
     */
    List<ErpUserJurisdictionPOJO> queryErpUserJurisByUserId(Long userId);
}

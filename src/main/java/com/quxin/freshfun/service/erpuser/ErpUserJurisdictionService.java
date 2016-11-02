package com.quxin.freshfun.service.erpuser;

import com.quxin.freshfun.model.erpuser.ErpRolePOJO;
import com.quxin.freshfun.model.erpuser.ErpUserJurisdictionPOJO;

import java.util.List;

/**
 * Created by Ziming on 2016/10/28.
 */
public interface ErpUserJurisdictionService {

    Integer addErpUserJuris(ErpUserJurisdictionPOJO erpUserJuris);

    List<ErpUserJurisdictionPOJO> queryErpUserJurisByUserId(Long userId);
}

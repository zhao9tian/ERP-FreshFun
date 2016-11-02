package com.quxin.freshfun.service.erpuser.impl;

import com.quxin.freshfun.dao.ErpUserJurisdictionMapper;
import com.quxin.freshfun.model.erpuser.ErpUserJurisdictionPOJO;
import com.quxin.freshfun.service.erpuser.ErpUserJurisdictionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Ziming on 2016/10/31.
 */
@Service("erpUserJurisdictionService")
public class ErpUserJurisdictionServiceImpl implements ErpUserJurisdictionService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ErpUserJurisdictionMapper erpUserJurisdictionMapper;

    /**
     * 授权
     * @param erpUserJuris 授权对象
     * @return 受影响行数
     */
    @Override
    public Integer addErpUserJuris(ErpUserJurisdictionPOJO erpUserJuris) {
        if (erpUserJuris == null) {
            logger.warn("授权入参有误，对象为空");
            return null;
        }
        Integer result = erpUserJurisdictionMapper.insertErpUserJuris(erpUserJuris);
        if (result != 1) {
            logger.warn("service：授权数据插入失败", erpUserJuris);
        }
        return result;
    }

    /**
     * 根据用户id查询用户权限
     * @param userId 用户id
     * @return 授权信息集合
     */
    @Override
    public List<ErpUserJurisdictionPOJO> queryErpUserJurisByUserId(Long userId) {
        if (userId == null || userId == 0) {
            logger.warn("根据用户id查询用户权限时，userId为空");
            return null;
        }
        return erpUserJurisdictionMapper.selectErpUserJurisByUserId(userId);
    }
}

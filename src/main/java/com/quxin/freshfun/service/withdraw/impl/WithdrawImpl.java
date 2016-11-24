package com.quxin.freshfun.service.withdraw.impl;

import com.quxin.freshfun.dao.FlowMapper;
import com.quxin.freshfun.dao.WithdrawMapper;
import com.quxin.freshfun.model.withdraw.WithdrawPOJO;
import com.quxin.freshfun.service.withdraw.WithdrawService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 提现实现类
 * Created by qucheng on 16/11/22.
 */
@Service("withdrawService")
public class WithdrawImpl implements WithdrawService {

    private static Logger logger = LoggerFactory.getLogger(WithdrawImpl.class);

    @Autowired
    private WithdrawMapper withdrawMapper;

    @Autowired
    private FlowMapper flowMapper;

    @Override
    public Boolean addWithdraw(WithdrawPOJO withdrawPOJO) {
        if (validateWithdraw(withdrawPOJO)) {//非空校验
            //校验金额大小 可提现金额
            Integer avaliableMoney = queryAvailableMoney(withdrawPOJO.getAppId());
            Integer withdrawMoney = withdrawPOJO.getWithdrawMoney();
            if(avaliableMoney != null && withdrawMoney > avaliableMoney){
                logger.error("提现金额大于可提现金额");
                return false ;
            }
            try {
                withdrawPOJO.setCreated(System.currentTimeMillis()/1000);
                withdrawPOJO.setUpdated(System.currentTimeMillis()/1000);
                withdrawPOJO.setIsDeleted(0);
                withdrawPOJO.setState(0);//提现中
                if (withdrawMapper.insertWithdraw(withdrawPOJO) == 1) {
                    return true;
                }
            } catch (Exception e) {
                logger.error("保存提现申请到数据库异常" + e);
            }
        }
        return false;
    }

    /**
     * 保存提现申请实体校验
     *
     * @param withdrawPOJO 实体
     * @return 是否通过校验
     */
    private boolean validateWithdraw(WithdrawPOJO withdrawPOJO) {
        if (withdrawPOJO != null) {
            if (withdrawPOJO.getAppId() == null) {
                logger.error("商户Id为空");
                return false;
            }
            if (withdrawPOJO.getUserId() == null) {
                logger.error("用户Id为空");
                return false;
            }
            if (withdrawPOJO.getAccountType() == null) {
                logger.error("账户类型为空");
                return false;
            }
            if (withdrawPOJO.getAccountNum() == null || "".equals(withdrawPOJO.getAccountNum())) {
                logger.error("账户账号为空");
                return false;
            }
            if (withdrawPOJO.getWithdrawMoney() == null || 0 == withdrawPOJO.getWithdrawMoney()) {
                logger.error("提现金额为空");
                return false;
            }
        } else {
            logger.error("提现对象为空");
            return false;
        }
        return true;
    }

    @Override
    public List<WithdrawPOJO> queryWithdrawListByAppId(Long appId, Integer start, Integer pageSize) {
        if (appId != null) {
            return withdrawMapper.selectWithdrawListByAppId(appId, start, pageSize);
        } else {
            logger.error("appId为空");
        }
        return null;
    }

    @Override
    public Integer queryAvailableMoney(Long appId) {
        if (appId != null) {
            Integer balance = flowMapper.selectBalanceByAppId(appId);
            Integer countWithdrawingMoney = withdrawMapper.selectSumWithdrawingByAppId(appId);//没有记录返回0
            if(balance == null){
                balance = 0 ;//没有就默认0
            }
            if(countWithdrawingMoney == null){
                countWithdrawingMoney = 0 ; //没有就默认0
            }
            return balance - countWithdrawingMoney;
        } else {
            logger.error("appId为空");
        }
        return null;
    }

    @Override
    public Integer queryCountWithdrawByAppId(Long appId) {
        if (appId != null) {
            return withdrawMapper.selectCountWithdrawByAppId(appId);
        } else {
            logger.error("appId为空");
        }
        return null;
    }

    @Override
    public Integer queryTotalMoney(Long appId) {
        if (appId != null) {
            return withdrawMapper.selectTotalMoneyByAppId(appId);
        } else {
            logger.error("appId为空");
        }
        return null;
    }

    @Override
    public Integer queryUnrecordMoney(Long appId) {
        if (appId != null) {
            return withdrawMapper.selectUnrecordMoneyByAppId(appId);
        } else {
            logger.error("appId为空");
        }
        return null;
    }
}

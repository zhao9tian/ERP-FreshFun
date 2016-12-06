package com.quxin.freshfun.service.withdraw.impl;

import com.quxin.freshfun.dao.FlowMapper;
import com.quxin.freshfun.dao.WithdrawMapper;
import com.quxin.freshfun.model.flow.FlowPOJO;
import com.quxin.freshfun.model.outparam.WithdrawOutParam;
import com.quxin.freshfun.model.withdraw.WithdrawPOJO;
import com.quxin.freshfun.service.flow.FlowService;
import com.quxin.freshfun.service.withdraw.WithdrawService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private FlowService flowService;

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

    @Override
    public List<WithdrawOutParam> queryWithdraws(Integer curPage, Integer pageSize, Integer status) {
        if (curPage == null || curPage == 0 || pageSize == null || pageSize == 0) {
            logger.warn("查询提现申请时，service层入参有误");
            return null;
        }
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("pageSize",pageSize);
        map.put("begin",pageSize*(curPage-1));
        if(status!=100){
            map.put("state",status);
        }
        return withdrawMapper.selectWithdraws(map);
    }

    @Override
    public boolean dealWithdraw(Integer dealType, Long withdrawId, Long crmUserId,Long updated) {
        if(!checkParam(dealType,withdrawId,crmUserId)){
            return false;
        }
        Integer state;
        boolean result = false;
        if(dealType==1)
            state=10;
        else
            state=20;
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("updated",updated);
        map.put("withdrawId",withdrawId);
        map.put("state",state);
        map.put("handlerId",crmUserId);
        Integer resultW = withdrawMapper.dealWithdraw(map);
        if(resultW>0){
            if(dealType==1){
                WithdrawPOJO withdrawPOJO = withdrawMapper.selectWithdrawById(withdrawId);
                FlowPOJO flowPOJO = new FlowPOJO();
                flowPOJO.setAppId(withdrawPOJO.getAppId());
                flowPOJO.setFlowMoney(withdrawPOJO.getWithdrawMoney());
                flowPOJO.setCreated(System.currentTimeMillis() / 1000);//入账时间，提现时间
                flowPOJO.setUpdated(System.currentTimeMillis() / 1000);
                flowPOJO.setFlowType(1);
                flowPOJO.setIsDeleted(0);
                result = flowService.addFlow(flowPOJO);
            }else{
                result = true;
            }
        }
        return result;
    }

    private boolean checkParam(Integer dealType, Long withdrawId, Long crmUserId) {
        if(dealType==null){
            logger.warn("处理提现申请，处理类型为空");
            return false;
        }
        if(withdrawId==null||withdrawId==0){
            logger.warn("处理提现申请，申请id为空");
            return false;
        }
        if(crmUserId==null||crmUserId==0){
            logger.warn("处理提现申请，处理人id为空");
            return false;
        }
        return true;
    }

    @Override
    public Integer queryWithdrawCount(Integer state) {
        if(state==100)
            state=null;
        return withdrawMapper.selectWithdrawCount(state);
    }
}

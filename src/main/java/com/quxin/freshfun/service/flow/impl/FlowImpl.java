package com.quxin.freshfun.service.flow.impl;

import com.quxin.freshfun.dao.FlowMapper;
import com.quxin.freshfun.model.flow.FlowPOJO;
import com.quxin.freshfun.service.flow.FlowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 流水实现类
 * Created by qucheng on 16/11/22.
 */
@Service("flowService")
class FlowImpl implements FlowService {

    private static Logger logger = LoggerFactory.getLogger(FlowImpl.class);

    @Autowired
    private FlowMapper flowMapper;

    @Override
    public Boolean addFlow(FlowPOJO flow) {
        if(validateFlow(flow)){
            //最后一条记录的余额
            Integer lastestBalance = flowMapper.selectBalanceByAppId(flow.getAppId());
            Integer newBalance = 0;
            if(lastestBalance != null){//存在余额
                if(flow.getFlowType() == 0){//入账
                    newBalance = lastestBalance + flow.getFlowMoney();
                }else if(flow.getFlowType() == 1){
                    newBalance = lastestBalance - flow.getFlowMoney();
                    if(newBalance < 0){
                        logger.error("提现金额大于余额");
                        return false ;
                    }
                }
            }else{//不存在余额,无法提现,只会入账
                newBalance = flow.getFlowMoney();
                if(flow.getFlowType() == 1){
                    logger.error("提现金额大于余额");
                    return false;
                }
            }
            flow.setBalance(newBalance);
            flow.setCreated(System.currentTimeMillis()/1000);
            flow.setUpdated(System.currentTimeMillis()/1000);
            flow.setIsDeleted(0);//默认值
            try{
                if(flowMapper.insertFlow(flow) == 1){
                    System.out.println(flow.getFlowId());
                    return true ;
                }
            }catch (Exception e){
                logger.error("mybatis插入异常"+e);
            }

        }
        return false;
    }

    /**
     * 校验流水
     * @param flow 流水对象
     * @return 是否通过校验
     */
    private boolean validateFlow(FlowPOJO flow) {
        if(flow != null){
            if(flow.getAppId() == null || 0==flow.getAppId()){
                logger.error("appId为空");
                return false;
            }
            if(flow.getFlowType() == null){
                logger.error("流水类型为空");
                return false;
            }else{
                if(flow.getFlowType() == 0){
                    if(flow.getOrderId() == null ){
                        logger.error("入账记录orderId为空");
                        return false;
                    }
                }
            }
            if(flow.getFlowMoney() == null){
                logger.error("流水金额为空");
                return false;
            }
        }else{
            logger.error("流水对象为空");
            return false;
        }
        return true;
    }

    @Override
    public List<FlowPOJO> queryFlowListByAppId(Long appId , Integer start , Integer pageSize) {
        if(appId != null && 0 != appId){
            return flowMapper.selectFlowListByAppId(appId , start , pageSize);
        }else{
            logger.error("appId为空");
        }
        return null;
    }

    @Override
    public Integer queryCountByAppId(Long appId) {
        if(appId != null && 0 != appId){
            return flowMapper.selectCountByAppId(appId);
        }else{
            logger.error("appId为空");
        }
        return null;

    }

    @Override
    public FlowPOJO queryFlowByOrderId(Long orderId) {
        if(orderId != null && 0 != orderId){
            return flowMapper.selectFlowByOrderId(orderId);
        }else{
            logger.error("订单Id为空");
        }
        return null;
    }
}

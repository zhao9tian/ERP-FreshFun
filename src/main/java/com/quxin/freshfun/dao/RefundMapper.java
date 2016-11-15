package com.quxin.freshfun.dao;


import com.quxin.freshfun.model.order.RefundPOJO;

public interface RefundMapper {
	/**
	 * 根据订单编号查询退款详情
	 * @return
	 */
	RefundPOJO selectRefundByOrderId(Long orderId);
}

package com.quxin.freshfun.model.order;

/**
 * Created by fanyanlin on 2017/1/5.
 */
public class OrderNumParam {
    /**
     * 待付款
     */
    private int awaitPayment;
    /**
     * 待发货
     */
    private int awaitDelivery;
    /**
     * 待收货
     */
    private int takeGoods;
    /**
     * 退款中
     */
    private int refunding;
    /**
     * 退款完成
     */
    private int refunded;
    /**
     * 订单关闭
     */
    private int closeOrder;
    /**
     * 完成订单
     */
    private int finishOrder;
    /**
     * 所有订单
     */
    private int totalOrder;

    public int getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(int totalOrder) {
        this.totalOrder = totalOrder;
    }

    public int getFinishOrder() {
        return finishOrder;
    }

    public void setFinishOrder(int finishOrder) {
        this.finishOrder = finishOrder;
    }

    public int getAwaitPayment() {
        return awaitPayment;
    }

    public void setAwaitPayment(int awaitPayment) {
        this.awaitPayment = awaitPayment;
    }

    public int getAwaitDelivery() {
        return awaitDelivery;
    }

    public void setAwaitDelivery(int awaitDelivery) {
        this.awaitDelivery = awaitDelivery;
    }

    public int getTakeGoods() {
        return takeGoods;
    }

    public void setTakeGoods(int takeGoods) {
        this.takeGoods = takeGoods;
    }

    public int getRefunding() {
        return refunding;
    }

    public void setRefunding(int refunding) {
        this.refunding = refunding;
    }

    public int getRefunded() {
        return refunded;
    }

    public void setRefunded(int refunded) {
        this.refunded = refunded;
    }

    public int getCloseOrder() {
        return closeOrder;
    }

    public void setCloseOrder(int closeOrder) {
        this.closeOrder = closeOrder;
    }
}

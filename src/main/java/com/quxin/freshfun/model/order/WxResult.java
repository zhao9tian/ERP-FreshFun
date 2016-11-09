package com.quxin.freshfun.model.order;

/**
 * Created by gsix on 2016/11/3.
 */
public class WxResult {
    /**
     * 微信退款返回的状态
     */
    private String return_code;
    /**
     * 微信退款返回的提示信息
     */
    private String return_msg;

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }
}

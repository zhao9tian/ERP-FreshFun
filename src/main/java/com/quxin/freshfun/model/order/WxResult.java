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

    private String result_code;

    private String err_code_des;
    /**
     * 错误信息
     * @return
     */
    public String getErr_code_des() {
        return err_code_des;
    }

    public void setErr_code_des(String err_code_des) {
        this.err_code_des = err_code_des;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

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

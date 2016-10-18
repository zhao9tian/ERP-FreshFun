package com.quxin.freshfun.utils;

import java.text.DecimalFormat;

/**
 * 金额转换工具类
 * Created by qucheng on 2016/10/12.
 */
public class MoneyFormatUtils {

    /**
     * 将传入int型金额除以100以double保留两位小数的字符串形式传出
     * @param price 传入的int行金额
     * @return 返回保留两位小数的字符串
     */
    public static String getMoneyFromInteger(Integer price){
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(((double) price)/100);
    }
}

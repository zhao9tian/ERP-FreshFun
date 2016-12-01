package com.quxin.freshfun.test;

import com.alibaba.fastjson.JSON;
import com.quxin.freshfun.model.goods.GoodsStandardPOJO;
import com.quxin.freshfun.utils.ResultUtil;
import org.apache.commons.beanutils.BeanUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by qucheng on 2016/10/17.
 */
public class Test {
    public static void main(String[] args) {


        GoodsStandardPOJO standardPOJO = new GoodsStandardPOJO();
        standardPOJO.setCoffeeType("coffee");
        standardPOJO.setAgtron("哈哈");
        Map<String , Object> map = ResultUtil.bean2map(standardPOJO);
        System.out.println(map);
//        BeanUtils.populate();




    }
}

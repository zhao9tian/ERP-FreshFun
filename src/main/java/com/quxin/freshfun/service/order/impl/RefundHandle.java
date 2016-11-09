package com.quxin.freshfun.service.order.impl;

import com.quxin.freshfun.model.order.WxResult;
import com.quxin.freshfun.utils.*;
import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;

import java.security.KeyStore;
import java.util.*;

/**
 * Created by qingtian on 2016/11/2.
 */
public class RefundHandle {
    /**
     * 请求参数
     */
    private SortedMap<String,Object> parameters;

    /**
     * 构造函数
      */
    public RefundHandle(){
        //初始化
        parameters = new TreeMap<>();
    }

    /**
     * 设置参数值
     * @param parameter 参数名称
     * @param parameterValue  参数值
     */
    public void setParameters(String parameter,String parameterValue){
        String v = "";
        if(null != parameterValue) {
            v = parameterValue.trim();
        }
        this.parameters.put(parameter,v);
    }

    /**
     * 发送请求
     * @param keyStore
     * @return
     */
    public WxResult sendRefund(KeyStore keyStore,String partner){
        Set<Map.Entry<String, Object>> entries = this.parameters.entrySet();
        String sendStr = getSendStr(entries);
        //json to xml
        String sendXML = jsonToXml(sendStr);
        //发送请求
        String resultXml = HttpClientUtils.doPost(ConstantUtil.REFUND_URL,partner, sendXML, keyStore);
        String resultJson = xmlToJson(resultXml);
        WxResult result = new WxResult();
        return WxUtlis.strToJson(resultJson,result);
    }

    /**
     * 签名
     * @return 参数签名后的字符串
     */
    public String createMD5Sign(String partnerKey){
        StringBuilder sb = new StringBuilder();
        StringBuilder resultStr = new StringBuilder();
        Set<Map.Entry<String, Object>> entries = this.parameters.entrySet();
        Iterator<Map.Entry<String, Object>> it = entries.iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            String k = entry.getKey();
            Object v = entry.getValue();
            sb.append(k + "=" + v + "&");
        }
        resultStr.append(sb.substring(0, sb.lastIndexOf("&")));
        resultStr.append("&key=");
        resultStr.append(partnerKey);
        return MD5Util.MD5Encode(resultStr.toString(),"UTF-8").toUpperCase();
    }

    /**
     * 获取发送请求str
     * @param entries 参数集合
     * @return
     */
    private String getSendStr(Set<Map.Entry<String, Object>> entries) {
        StringBuilder sb = new StringBuilder();
        StringBuilder resultStr = new StringBuilder();
        resultStr.append("{");
        Iterator<Map.Entry<String, Object>> it = entries.iterator();
        while (it.hasNext()){
            Map.Entry<String, Object> entry = it.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            if(null != value && !"".equals(value)){
                sb.append("\"" + key + "\":\"" + value + "\",");
            }
        }
        resultStr.append(sb.substring(0,sb.lastIndexOf(",")));
        resultStr.append("}");
        return resultStr.toString();
    }

    /**
     * json字符串转Xml字符串
     * @param jsonStr
     * @return
     */
    public String jsonToXml(String jsonStr){
        XMLSerializer xmlSerializer = new XMLSerializer();
        xmlSerializer.setRootName("xml");
        xmlSerializer.clearNamespaces("xml");
        return xmlSerializer.write(JSONSerializer.toJSON(jsonStr));
    }

    /**
     * xml格式转json
     * @return
     */
    public String xmlToJson(String xmlStr){
        return new XMLSerializer().read(xmlStr).toString();
    }
}

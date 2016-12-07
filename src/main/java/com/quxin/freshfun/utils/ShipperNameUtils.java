package com.quxin.freshfun.utils;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 根据物流公司code获取物流公司名称
 * Created by qucheng on 16/12/6.
 */
public class ShipperNameUtils {

    private static Map<Object , Object> shipper = new HashMap<>();

    static {
        String jsonString = "[\n" +
                "{\"code\":\"AJ\",\"name\":\"安捷快递\"},\n" +
                "{\"code\":\"ANE\",\"name\":\"安能物流\"},\n" +
                "{\"code\":\"AXD\",\"name\":\"安信达快递\"},\n" +
                "{\"code\":\"BQXHM\",\"name\":\"北青小红帽\"},\n" +
                "{\"code\":\"BFDF\",\"name\":\"百福东方\"},\n" +
                "{\"code\":\"BTWL\",\"name\":\"百世快运\"},\n" +
                "{\"code\":\"CCES\",\"name\":\"CCES快递\"},\n" +
                "{\"code\":\"CITY100\",\"name\":\"城市100\"},\n" +
                "{\"code\":\"COE\",\"name\":\"COE东方快递\"},\n" +
                "{\"code\":\"CSCY\",\"name\":\"长沙创一\"},\n" +
                "{\"code\":\"CDSTKY\",\"name\":\"成都善途速运\"},\n" +
                "{\"code\":\"DBL\",\"name\":\"德邦\"},\n" +
                "{\"code\":\"DSWL\",\"name\":\"D速物流\"},\n" +
                "{\"code\":\"DTWL\",\"name\":\"大田物流\"},\n" +
                "{\"code\":\"EMS\",\"name\":\"EMS\"},\n" +
                "{\"code\":\"FAST\",\"name\":\"快捷速递\"},\n" +
                "{\"code\":\"FEDEX\",\"name\":\"FEDEX联邦(国内件）\"},\n" +
                "{\"code\":\"FEDEX_GJ\",\"name\":\"FEDEX联邦(国际件）\"},\n" +
                "{\"code\":\"FKD\",\"name\":\"飞康达\"},\n" +
                "{\"code\":\"GDEMS\",\"name\":\"广东邮政\"},\n" +
                "{\"code\":\"GSD\",\"name\":\"共速达\"},\n" +
                "{\"code\":\"GTO\",\"name\":\"国通快递\"},\n" +
                "{\"code\":\"GTSD\",\"name\":\"高铁速递\"},\n" +
                "{\"code\":\"HFWL\",\"name\":\"汇丰物流\"},\n" +
                "{\"code\":\"HHTT\",\"name\":\"天天快递\"},\n" +
                "{\"code\":\"HLWL\",\"name\":\"恒路物流\"},\n" +
                "{\"code\":\"HOAU\",\"name\":\"天地华宇\"},\n" +
                "{\"code\":\"hq568\",\"name\":\"华强物流\"},\n" +
                "{\"code\":\"HTKY\",\"name\":\"百世快递\"},\n" +
                "{\"code\":\"HXLWL\",\"name\":\"华夏龙物流\"},\n" +
                "{\"code\":\"HYLSD\",\"name\":\"好来运快递\"},\n" +
                "{\"code\":\"JGSD\",\"name\":\"京广速递\"},\n" +
                "{\"code\":\"JIUYE\",\"name\":\"九曳供应链\"},\n" +
                "{\"code\":\"JJKY\",\"name\":\"佳吉快运\"},\n" +
                "{\"code\":\"JLDT\",\"name\":\"嘉里物流\"},\n" +
                "{\"code\":\"JTKD\",\"name\":\"捷特快递\"},\n" +
                "{\"code\":\"JXD\",\"name\":\"急先达\"},\n" +
                "{\"code\":\"JYKD\",\"name\":\"晋越快递\"},\n" +
                "{\"code\":\"JYM\",\"name\":\"加运美\"},\n" +
                "{\"code\":\"JYWL\",\"name\":\"佳怡物流\"},\n" +
                "{\"code\":\"KYWL\",\"name\":\"跨越物流\"},\n" +
                "{\"code\":\"LB\",\"name\":\"龙邦快递\"},\n" +
                "{\"code\":\"LHT\",\"name\":\"联昊通速递\"},\n" +
                "{\"code\":\"MHKD\",\"name\":\"民航快递\"},\n" +
                "{\"code\":\"MLWL\",\"name\":\"明亮物流\"},\n" +
                "{\"code\":\"NEDA\",\"name\":\"能达速递\"},\n" +
                "{\"code\":\"PADTF\",\"name\":\"平安达腾飞快递\"},\n" +
                "{\"code\":\"QCKD\",\"name\":\"全晨快递\"},\n" +
                "{\"code\":\"QFKD\",\"name\":\"全峰快递\"},\n" +
                "{\"code\":\"QRT\",\"name\":\"全日通快递\"},\n" +
                "{\"code\":\"RFD\",\"name\":\"如风达\"},\n" +
                "{\"code\":\"SAD\",\"name\":\"赛澳递\"},\n" +
                "{\"code\":\"SAWL\",\"name\":\"圣安物流\"},\n" +
                "{\"code\":\"SBWL\",\"name\":\"盛邦物流\"},\n" +
                "{\"code\":\"SDWL\",\"name\":\"上大物流\"},\n" +
                "{\"code\":\"SF\",\"name\":\"顺丰快递\"},\n" +
                "{\"code\":\"SFWL\",\"name\":\"盛丰物流\"},\n" +
                "{\"code\":\"SHWL\",\"name\":\"盛辉物流\"},\n" +
                "{\"code\":\"ST\",\"name\":\"速通物流\"},\n" +
                "{\"code\":\"STO\",\"name\":\"申通快递\"},\n" +
                "{\"code\":\"STWL\",\"name\":\"速腾快递\"},\n" +
                "{\"code\":\"SURE\",\"name\":\"速尔快递\"},\n" +
                "{\"code\":\"TSSTO\",\"name\":\"唐山申通\"},\n" +
                "{\"code\":\"UAPEX\",\"name\":\"全一快递\"},\n" +
                "{\"code\":\"UC\",\"name\":\"优速快递\"},\n" +
                "{\"code\":\"WJWL\",\"name\":\"万家物流\"},\n" +
                "{\"code\":\"WXWL\",\"name\":\"万象物流\"},\n" +
                "{\"code\":\"XBWL\",\"name\":\"新邦物流\"},\n" +
                "{\"code\":\"XFEX\",\"name\":\"信丰快递\"},\n" +
                "{\"code\":\"XYT\",\"name\":\"希优特\"},\n" +
                "{\"code\":\"XJ\",\"name\":\"新杰物流\"},\n" +
                "{\"code\":\"YADEX\",\"name\":\"源安达快递\"},\n" +
                "{\"code\":\"YCWL\",\"name\":\"远成物流\"},\n" +
                "{\"code\":\"YD\",\"name\":\"韵达快递\"},\n" +
                "{\"code\":\"YDH\",\"name\":\"义达国际物流\"},\n" +
                "{\"code\":\"YFEX\",\"name\":\"越丰物流\"},\n" +
                "{\"code\":\"YFHEX\",\"name\":\"原飞航物流\"},\n" +
                "{\"code\":\"YFSD\",\"name\":\"亚风快递\"},\n" +
                "{\"code\":\"YTKD\",\"name\":\"运通快递\"},\n" +
                "{\"code\":\"YTO\",\"name\":\"圆通速递\"},\n" +
                "{\"code\":\"YXKD\",\"name\":\"亿翔快递\"},\n" +
                "{\"code\":\"YZPY\",\"name\":\"邮政平邮/小包\"},\n" +
                "{\"code\":\"ZENY\",\"name\":\"增益快递\"},\n" +
                "{\"code\":\"ZHQKD\",\"name\":\"汇强快递\"},\n" +
                "{\"code\":\"ZJS\",\"name\":\"宅急送\"},\n" +
                "{\"code\":\"ZTE\",\"name\":\"众通快递\"},\n" +
                "{\"code\":\"ZTKY\",\"name\":\"中铁快运\"},\n" +
                "{\"code\":\"ZTO\",\"name\":\"中通速递\"},\n" +
                "{\"code\":\"ZTWL\",\"name\":\"中铁物流\"},\n" +
                "{\"code\":\"ZYWL\",\"name\":\"中邮物流\"},\n" +
                "{\"code\":\"AMAZON\",\"name\":\"亚马逊物流\"},\n" +
                "{\"code\":\"SUBIDA\",\"name\":\"速必达物流\"},\n" +
                "{\"code\":\"RFEX\",\"name\":\"瑞丰速递\"},\n" +
                "{\"code\":\"QUICK\",\"name\":\"快客快递\"},\n" +
                "{\"code\":\"CJKD\",\"name\":\"城际快递\"},\n" +
                "{\"code\":\"CNPEX\",\"name\":\"CNPEX中邮快递\"},\n" +
                "{\"code\":\"HOTSCM\",\"name\":\"鸿桥供应链\"},\n" +
                "{\"code\":\"HPTEX\",\"name\":\"海派通物流公司\"},\n" +
                "{\"code\":\"AYCA\",\"name\":\"澳邮专线\"},\n" +
                "{\"code\":\"PANEX\",\"name\":\"泛捷快递\"},\n" +
                "{\"code\":\"PCA\",\"name\":\"PCA Express\"},\n" +
                "{\"code\":\"UEQ\",\"name\":\"UEQ Express\"}\n" +
                "]";
        List<Map> shipperList = JSON.parseArray(jsonString, Map.class);
        for(Map map : shipperList){
            shipper.put(map.get("code"),map.get("name"));
        }
    }

    public static String getShipperNameByCode(String shipperCode){

        return (String) shipper.get(shipperCode);
    }

}

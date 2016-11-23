package com.quxin.freshfun.test;

import com.google.common.collect.Maps;
import com.quxin.freshfun.model.goods.GoodsPOJO;
import com.quxin.freshfun.service.goods.GoodsService;
import com.quxin.freshfun.utils.MoneyFormatUtils;
import org.apache.poi.hssf.usermodel.*;
import org.junit.After;
import org.junit.Before;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static javafx.scene.input.KeyCode.M;

/**
 * 商品单元测试
 * Created by qucheng on 2016/10/18.
 */
public class ExportExcelTest extends TestBase {


    private GoodsService goodsService;


    @Before
    public void setUp() throws Exception {
        goodsService = getContext().getBean("goodsService", GoodsService.class);
    }


    @After
    public void tearDown() throws Exception {
        getContext().close();
    }


    @org.junit.Test
    public void queryAllGoods() throws Exception {
        Map<String, Object> qc = Maps.newHashMap();
        qc.put("pageSize", 400);
        List<GoodsPOJO> goods = goodsService.queryAllGoods(qc);
        List<GoodsPOJO> goodslist = new ArrayList<>();
        for (GoodsPOJO g : goods) {
            goodslist.add(goodsService.queryGoodsByGoodsId(g.getGoodsId()));
        }
        System.out.println(goods.size());
        exportExcel(goodslist);

    }


    private static void exportExcel(List<GoodsPOJO> list) throws Exception {
        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("商品表");
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow( 0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

        HSSFCell cell = row.createCell( 0);
        cell.setCellValue("所属类目");
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue("商品Id");
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellValue("品名");
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellValue("标题");
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellValue("副标题");
        cell.setCellStyle(style);
        cell = row.createCell(5);
        cell.setCellValue("小编说");
        cell.setCellStyle(style);
        cell = row.createCell(6);
        cell.setCellValue("成本价");
        cell.setCellStyle(style);
        cell = row.createCell(7);
        cell.setCellValue("平台原价");
        cell.setCellStyle(style);
        cell = row.createCell(8);
        cell.setCellValue("平台售价");
        cell.setCellStyle(style);
        cell = row.createCell(9);
        cell.setCellValue("毛利率");
        cell.setCellStyle(style);
        cell = row.createCell(10);
        cell.setCellValue("商城链接");
        cell.setCellStyle(style);

        // 第五步，写入实体数据 实际应用中这些数据从数据库得到，

        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            GoodsPOJO goods = list.get(i);
            // 第四步，创建单元格，并设置值
            row.createCell(0).setCellValue(getCatogary(goods.getCategory2()));
            row.createCell(1).setCellValue(goods.getGoodsId());
            row.createCell(2).setCellValue(goods.getGoodsStandardPOJO().getName());
            row.createCell(3).setCellValue(goods.getTitle());
            row.createCell(4).setCellValue(goods.getSubtitle());
            row.createCell(5).setCellValue(goods.getGoodsDes());
            row.createCell(6).setCellValue(MoneyFormatUtils.getMoneyFromInteger(goods.getGoodsCost()));
            row.createCell(7).setCellValue(MoneyFormatUtils.getMoneyFromInteger(goods.getOriginPrice()));
            row.createCell(8).setCellValue(MoneyFormatUtils.getMoneyFromInteger(goods.getShopPrice()));
            row.createCell(9).setCellValue(((double)(goods.getShopPrice()-goods.getGoodsCost()))/(double)goods.getShopPrice());
            row.createCell(10).setCellValue("https://www.freshfun365.com/api/app/FreshFunApp/goodsInfo.html?goodsId="+goods.getGoodsId());
        }
        // 第六步，将文件存到指定位置
        try {
            FileOutputStream fout = new FileOutputStream("/Users/qucheng/Desktop/goods.xls");
            wb.write(fout);
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getCatogary(Integer catogary2){
        String catogary = "";
        switch (catogary2){
            case 101 :
                catogary = "茶水冲饮";
                break;
            case 102 :
                catogary = "滋补保健";
                break;
            case 103 :
                catogary = "糖巧点心";
                break;
            case 104 :
                catogary = "速食/调味";
                break;
            case 105 :
                catogary = "厨具/餐具";
                break;
            case 106 :
                catogary = "零食/坚果";
                break;
            case 107 :
                catogary = "酒水";
                break;
            case 108 :
                catogary = "生鲜蔬果";
                break;
        }
        return catogary;
    }


    public static void main(String[] args) {
        DecimalFormat df = new DecimalFormat("0.00");
        double a = 19.9;
        df.format(19.9);
        BigDecimal aa = new BigDecimal(a);
        BigDecimal bb = new BigDecimal(100);
//        System.out.println(Double.parseDouble(df.format())*100);//自动四舍五入

        System.out.println(1990d/100);//
        System.out.println(9.9*100);//
//        System.out.println(Math.round(19));//
    }

}

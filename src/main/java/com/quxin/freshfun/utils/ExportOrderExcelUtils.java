package com.quxin.freshfun.utils;

import com.quxin.freshfun.model.order.OrderDetailsPOJO;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by fanyanlin on 2016/11/14.
 * 订单导出工具类
 */
public class ExportOrderExcelUtils {

    private XSSFWorkbook xw = null;

    private XSSFSheet sheet = null;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private String[] titles = {"订单编号","用户编号", "商品名", "成交价", "单价", "数量", "成本价", "成交时间", "订单来源","粉丝来源","订单状态", "收货人","电话", "收货地址","快递公司","物流编号","备注"};

    public ExportOrderExcelUtils(){
        // 创建一个workbook 对应一个excel应用文件
        this.xw = new XSSFWorkbook();
    }

    /**
     * 设置表头的单元格样式
     *
     * @return
     */
    private XSSFCellStyle getHeadStyle() {
        // 创建单元格样式
        XSSFCellStyle cellStyle = xw.createCellStyle();
        // 设置单元格的背景颜色为淡蓝色
        cellStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        // 设置单元格居中对齐
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        // 设置单元格垂直居中对齐
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        // 创建单元格内容显示不下时自动换行
        cellStyle.setWrapText(true);
        // 设置单元格字体样式
        XSSFFont font = xw.createFont();
        // 设置字体加粗
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
        font.setFontHeight((short) 200);
        cellStyle.setFont(font);
        // 设置单元格边框为细线条
        cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
        return cellStyle;
    }

    /**
     * 设置表体的单元格样式
     *
     * @return
     */
    private XSSFCellStyle getBodyStyle() {
        // 创建单元格样式
        XSSFCellStyle cellStyle = xw.createCellStyle();
        // 设置单元格居中对齐
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        // 设置单元格垂直居中对齐
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        // 创建单元格内容显示不下时自动换行
        cellStyle.setWrapText(true);
        // 设置单元格字体样式
        XSSFFont font = xw.createFont();
        // 设置字体加粗
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
        font.setFontHeight((short) 200);
        cellStyle.setFont(font);
        // 设置单元格边框为细线条
        cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
        return cellStyle;
    }

    /**
     * 到处订单Excel
     * @param orderList
     * @param outputStream
     */
    public void ExportExcel(List<OrderDetailsPOJO> orderList, ServletOutputStream outputStream,Integer orderState) {

        String state = getSheet(orderState);
        this.sheet = xw.createSheet(state);
        XSSFCellStyle headStyle = getHeadStyle();
        XSSFCellStyle bodyStyle = getBodyStyle();

        // 构建表头
        XSSFRow headRow = this.sheet.createRow(0);
        XSSFCell cell = null;

        // 输出标题
        for (int i = 0; i < titles.length; i++) {
            cell = headRow.createCell(i);
            cell.setCellStyle(headStyle);
            cell.setCellValue(titles[i]);
        }

        for (int i = 0;i < orderList.size() ; i++) {
            XSSFRow bodyRow = this.sheet.createRow(i + 1);
            OrderDetailsPOJO order = orderList.get(i);
            //编号
            cell = bodyRow.createCell(0);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue(order.getOrderId());
            //用户编号
            cell = bodyRow.createCell(1);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue(order.getUserId());
            //商品名
            cell = bodyRow.createCell(2);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue(order.getGoods().getGoodsName());
            //成交价
            cell = bodyRow.createCell(3);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue(order.getActualMoney());
            //单价
            cell = bodyRow.createCell(4);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue(order.getPayMoney());
            //数量
            cell = bodyRow.createCell(5);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue(order.getCount());
            //成本价
            cell = bodyRow.createCell(6);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue(order.getCostMoney());
            //成交时间
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            cell = bodyRow.createCell(7);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue(dateFormat.format(order.getPayTime()*1000));
            //订单来源
            cell = bodyRow.createCell(8);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue(order.getAppId());
            //粉丝来源
            cell = bodyRow.createCell(9);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue(order.getFansAppId());
            //订单状态
            cell = bodyRow.createCell(10);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue(getSheet(order.getOrderStatus()));
            //收货人
            cell = bodyRow.createCell(11);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue(order.getName());
            //电话
            cell = bodyRow.createCell(12);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue(order.getTel());
            //收货地址
            cell = bodyRow.createCell(13);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue(order.getCity()+order.getAddress());
            //快递公司
            cell = bodyRow.createCell(14);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue(order.getDeliveryName());
            //物流编号
            cell = bodyRow.createCell(15);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue(order.getDeliveryNum());
            //备注
            cell = bodyRow.createCell(16);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue(order.getRemark());
        }
        try {
            xw.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                logger.error("导出订单Excel异常",e);
            }
        }
    }

    private String getSheet(Integer orderState) {
        String state = "";
        switch (orderState){
            case 10:
                state = "未支付订单";
                break;
            case 15:
                state = "关闭订单";
                break;
            case 20:
                state = "退款完成订单";
                break;
            case 30:
                state = "已支付(待发货订单)";
                break;
            case 40:
                state = "退款中订单";
                break;
            case 50:
                state = "待收货订单";
                break;
            case 70:
                state = "已完成订单";
                break;
            case 100:
                state = "已完成订单";
                break;
            default:
                state = "所有订单";
                break;
        }
        return state;
    }
}

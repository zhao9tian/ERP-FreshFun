package com.quxin.freshfun.utils;

import com.quxin.freshfun.model.order.OrderDetailsPOJO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 导入excel处理工具类
 * Created by qucheng on 16/12/1.
 */
public class Stream2Bean {

    private static Logger logger = LoggerFactory.getLogger(Stream2Bean.class);

    /**
     * 转换excel流为订单list --只包含物流信息
     * @param inputStream 文件流
     * @return 订单列表
     */
    public static List<OrderDetailsPOJO> getOrdersByInputStream(InputStream inputStream) {
        try {
            List<OrderDetailsPOJO> orderDetailsPOJOs = new ArrayList<>();
            FileInputStream fis = (FileInputStream) inputStream;//文件流
            Workbook workbook = new XSSFWorkbook(fis);//整个excel对象
            Sheet sheet = workbook.getSheetAt(0);//此处只有一个
            for (int row = 1; row <= sheet.getLastRowNum(); row++) {//去除第一行
                Row rowObject = sheet.getRow(row);
                if(rowObject.getCell(0).getCellType()!=Cell.CELL_TYPE_BLANK){//没有数据了
                    OrderDetailsPOJO orderDetailsPOJO = new OrderDetailsPOJO();
                    try {
                        if(rowObject.getCell(0).getCellType()!=Cell.CELL_TYPE_BLANK){
                            rowObject.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                            Long orderId = Long.parseLong(rowObject.getCell(0).getStringCellValue());//订单号
                            orderDetailsPOJO.setOrderId(orderId);
                        }
                        if(rowObject.getCell(2).getCellType()!=Cell.CELL_TYPE_BLANK){
                            rowObject.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                            String logisticCode = rowObject.getCell(2).getStringCellValue(); //物流单号
                            orderDetailsPOJO.setDeliveryNum(logisticCode);
                        }
                        if(rowObject.getCell(3).getCellType()!=Cell.CELL_TYPE_BLANK){
                            rowObject.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                            String goodscost = rowObject.getCell(3).getStringCellValue(); //成本价
                            orderDetailsPOJO.setActualMoney(goodscost);
                        }
                        String shipperCode = rowObject.getCell(1).getStringCellValue();//公司编号
                        orderDetailsPOJO.setDeliveryName(shipperCode);
                        orderDetailsPOJOs.add(orderDetailsPOJO);
                    } catch (ClassCastException e) {
                        logger.error("订单号为" + rowObject.getCell(0).getStringCellValue() + "的记录格式错误");
                    }
                }
            }
            fis.close();
            return orderDetailsPOJOs ;
        } catch (IOException e) {
            logger.error("关闭文件流异常");
        }
        return null;
    }

}

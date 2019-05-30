package com.casic.demo.utils;

import java.io.*;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;



/**
 * excel 工具类
 */
public class POIUtil {

   /* private static String downLoadPath = "D:/testDownloads/处理结果1.txt";
    //private static String downLoadName = "处理结果.txt";
    *//**
     * 读取excel内容 并写入到文本文件中
     * @param filePath
     *//*
    public static void readExcel (String filePath) {
        try {
            BufferedWriter writer;
            XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(filePath));
            //创建目录 及 文件
            createFile(downLoadPath);
            writer = new BufferedWriter(new FileWriter(downLoadPath));
            XSSFSheet sheet = wb.getSheetAt(0);
            Iterator<Row> iterator = sheet.rowIterator();
            int count =0;
            while (iterator.hasNext()) {
                XSSFRow row = (XSSFRow) iterator.next();
                if (count == 0) {
                    count++;
                    continue;
                }
                String columnOne = getValue(row.getCell(0));
                //String columnTwo = getValue(row.getCell(1));
                if (columnOne.contains("_")) {
                    int position = columnOne.indexOf("_");
                    columnOne = columnOne.substring(0,position)+ StringUtils.capitalize(columnOne.substring(position+1));
                }
                writer.write("处理excel 内容 导出 第"+count+"行: 第一列:"+columnOne);
                count++;
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    *//**
     * 获取EXCEL cell中的值
     * @param cell
     * @return
     *//*
    private static String getValue(XSSFCell cell) {
        String value;
        switch (cell.getCellType()) {
            case XSSFCell.CELL_TYPE_STRING:
                value = cell.getStringCellValue();
                break;
            case XSSFCell.CELL_TYPE_NUMERIC:
                value = String.valueOf(cell.getNumericCellValue());
                break;
            default:
                value = cell.getRawValue();
        }
        return value;
    }

    *//**
     * 创建文件
     * @param fileName
     * @return
     *//*
    private static boolean createFile(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            return false;
        }
        if (fileName.endsWith(File.separator)) {
            return false;
        }
        //判断文件所在目录 是否存在
        if (!file.getParentFile().exists()) {
            //目录不存在 创建目录
            if (!file.getParentFile().mkdir()) {
                return false;
            }
        }
        try {
            //创建文件
            if (file.createNewFile()) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            //创建文件异常
            return false;
        }
    }*/
}

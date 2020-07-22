package com.base.frame.common.utils;


import org.apache.poi.hssf.usermodel.*;

import java.lang.reflect.Field;
import java.util.*;

public class ExcelUtil {

    /**
     * 生成excel
     * @param excelName
     * @param list
     * @param <T>
     * @return
     */
    public static <T> HSSFWorkbook generateExcel(String excelName, List<T> list) {
        try {
            //创建一个WorkBook,对应一个Excel文件
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
            //在Workbook中，创建一个sheet，对应Excel中的工作薄（sheet）
            HSSFSheet sheet = hssfWorkbook.createSheet(excelName);
            //创建单元格，并设置值表头 设置表头居中
            HSSFCellStyle style = hssfWorkbook.createCellStyle();
            //获取表头
            Map<Integer, String> headMap = new HashMap<>();
            Field[] declaredFields = list.get(0).getClass().getDeclaredFields();
            Arrays.stream(declaredFields).forEach(x -> {
                ExportAnnotation exportAnnotation = x.getAnnotation(ExportAnnotation.class);
                if (exportAnnotation != null) {
                    headMap.put(exportAnnotation.index(), x.getName() + "," + exportAnnotation.name());
                }
            });
            //在sheet中添加表头第0行
            HSSFRow row = sheet.createRow(0);
            // 填充表头
            for (Map.Entry<Integer, String> entry : headMap.entrySet()) {
                HSSFCell cell = row.createCell(entry.getKey());
                cell.setCellValue(entry.getValue().split(",")[1]);
                cell.setCellStyle(style);
                sheet.autoSizeColumn(entry.getKey());
            }

            // 填充内容
            for (int i = 0; i < list.size(); i++) {
                row = sheet.createRow(i + 1);
                T data = list.get(i);
                for (Map.Entry<Integer, String> entry : headMap.entrySet()) {
                    String fieldName = entry.getValue().split(",")[0];
                    Object objValue = getFieldValueByName(fieldName, data);
                    String fieldValue = objValue == null ? "" : objValue.toString();
                    HSSFCell cell = row.createCell(entry.getKey());
                    cell.setCellStyle(style);
                    sheet.autoSizeColumn(entry.getKey());
                    cell.setCellValue(fieldValue);
                }
            }
            return hssfWorkbook;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成excel
     *
     * @param excelName 要导出的excel名称
     * @param list      要导出的数据集合
     * @param fieldMap  中英文字段对应Map,即要导出的excel表头
     * @return
     */
    public static <T> HSSFWorkbook generateExcel(String excelName, List<T> list, LinkedHashMap<String, String> fieldMap) {
        try {
            //创建一个WorkBook,对应一个Excel文件
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
            //在Workbook中，创建一个sheet，对应Excel中的工作薄（sheet）
            HSSFSheet sheet = hssfWorkbook.createSheet(excelName);
            //创建单元格，并设置值表头 设置表头居中
            HSSFCellStyle style = hssfWorkbook.createCellStyle();
            // 填充工作表
            fillSheet(sheet, list, fieldMap, style);
            return hssfWorkbook;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成excel文件流
     *
     * @param sheetName
     * @param header
     * @param body
     * @return
     */
    public static HSSFWorkbook generateExcel(String sheetName, List<String> header, List<List<String>> body) {
        try {
            //新建excel报表
            HSSFWorkbook excel = new HSSFWorkbook();
            //添加一个sheet
            HSSFSheet hssfSheet = excel.createSheet(sheetName);
            //往excel表格创建一行，excel的行号是从0开始的
            // 设置表头
            HSSFRow firstRow = hssfSheet.createRow(0);
            for (int columnNum = 0; columnNum < header.size(); columnNum++) {
                //创建单元格
                HSSFCell hssfCell = firstRow.createCell(columnNum);
                //设置单元格的值
                hssfCell.setCellValue(header.size() < columnNum ? "-" : header.get(columnNum));
            }
            // 设置主体数据
            for (int rowNum = 0; rowNum < body.size(); rowNum++) {
                //往excel表格创建一行，excel的行号是从0开始的
                HSSFRow hssfRow = hssfSheet.createRow(rowNum + 1);
                List<String> data = body.get(rowNum);
                for (int columnNum = 0; columnNum < data.size(); columnNum++) {
                    //创建单元格
                    HSSFCell hssfCell = hssfRow.createCell(columnNum);
                    //设置单元格的值
                    hssfCell.setCellValue(data.size() < columnNum ? "-" : data.get(columnNum));
                }
            }
            return excel;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 向工作表中填充数据
     *
     * @param sheet    excel的工作表名称
     * @param list     数据源
     * @param fieldMap 中英文字段对应关系的Map
     * @param style    表格中的格式
     * @throws Exception 异常
     */
    private static <T> void fillSheet(HSSFSheet sheet, List<T> list,
                                      LinkedHashMap<String, String> fieldMap, HSSFCellStyle style) throws Exception {
        // 定义存放英文字段名和中文字段名的数组
        String[] enFields = new String[fieldMap.size()];
        String[] cnFields = new String[fieldMap.size()];

        // 填充数组
        int count = 0;
        for (Map.Entry<String, String> entry : fieldMap.entrySet()) {
            enFields[count] = entry.getKey();
            cnFields[count] = entry.getValue();
            count++;
        }

        //在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow(0);

        // 填充表头
        for (int i = 0; i < cnFields.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(cnFields[i]);
            cell.setCellStyle(style);
            sheet.autoSizeColumn(i);
        }

        // 填充内容
        for (int index = 0; index < list.size(); index++) {
            row = sheet.createRow(index + 1);
            // 获取单个对象
            T item = list.get(index);
            for (int i = 0; i < enFields.length; i++) {
                Object objValue = getFieldValueByNameSequence(enFields[i], item);
                String fieldValue = objValue == null ? "" : objValue.toString();
                row.createCell(i).setCellValue(fieldValue);
            }
        }
    }

    /**
     * 根据带路径或不带路径的属性名获取属性值,即接受简单属性名，
     * 如userName等，又接受带路径的属性名，如student.department.name等
     *
     * @param fieldNameSequence 带路径的属性名或简单属性名
     * @param o                 对象
     * @return 属性值
     * @throws Exception 异常
     */
    private static Object getFieldValueByNameSequence(String fieldNameSequence,
                                                      Object o) throws Exception {
        Object value = null;

        // 将fieldNameSequence进行拆分
        String[] attributes = fieldNameSequence.split("\\.");
        if (attributes.length == 1) {
            value = getFieldValueByName(fieldNameSequence, o);
        } else {
            // 根据数组中第一个连接属性名获取连接属性对象，如student.department.name
            Object fieldObj = getFieldValueByName(attributes[0], o);
            //截取除第一个属性名之后的路径
            String subFieldNameSequence = fieldNameSequence
                    .substring(fieldNameSequence.indexOf(".") + 1);
            //递归得到最终的属性对象的值
            value = getFieldValueByNameSequence(subFieldNameSequence, fieldObj);
        }
        return value;

    }

    /**
     * 根据字段名获取字段值
     *
     * @param fieldName 字段名
     * @param o         对象
     * @return 字段值
     * @throws Exception 异常
     */
    private static Object getFieldValueByName(String fieldName, Object o)
            throws Exception {

        Object value = null;
        //根据字段名得到字段对象
        Field field = getFieldByName(fieldName, o.getClass());

        //如果该字段存在，则取出该字段的值
        if (field != null) {
            //类中的成员变量为private,在类外边使用属性值，故必须进行此操作
            field.setAccessible(true);
            //获取当前对象中当前Field的value
            value = field.get(o);
        } else {
            throw new Exception(o.getClass().getSimpleName() + "类不存在字段名 "
                    + fieldName);
        }

        return value;
    }

    /**
     * 根据字段名获取字段对象
     *
     * @param fieldName 字段名
     * @param clazz     包含该字段的类
     * @return 字段
     */
    private static Field getFieldByName(String fieldName, Class<?> clazz) {
        // 拿到本类的所有字段
        Field[] selfFields = clazz.getDeclaredFields();

        // 如果本类中存在该字段，则返回
        for (Field field : selfFields) {
            //如果本类中存在该字段，则返回
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }

        // 否则，查看父类中是否存在此字段，如果有则返回
        Class<?> superClazz = clazz.getSuperclass();
        if (superClazz != null && superClazz != Object.class) {
            //递归
            return getFieldByName(fieldName, superClazz);
        }

        // 如果本类和父类都没有，则返回空
        return null;
    }

}
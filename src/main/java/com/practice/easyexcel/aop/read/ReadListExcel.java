package com.practice.easyexcel.aop.read;

import com.practice.easyexcel.aop.annotation.Excel;
import com.practice.easyexcel.aop.constants.Constants;
import com.practice.easyexcel.utils.ExcelUtil;
import com.practice.easyexcel.utils.MyStringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 读取excel
 *
 * @author liuyazhou
 */
public class ReadListExcel<T> implements ReadExcel<T> {

    private String inFilePath;

    private int beginRow;

    /**
     * 进行excel读取
     *
     * @param param
     * @param clazz
     * @return
     */
    @Override
    public List read(Map<String, Object> param, Class clazz) throws IOException, InstantiationException, IllegalAccessException {
        initParam(param, clazz);
        Workbook workbook = ExcelUtil.getWorkbookTypeByFile(inFilePath);
        if (workbook == null) {
            throw new FileNotFoundException();
        }
        return readList(workbook, clazz);
    }

    /**
     * 读取excel数据
     *
     * @param workbook
     * @param clazz
     * @return
     */
    private List<T> readList(Workbook workbook, Class<T> clazz) throws IllegalAccessException, InstantiationException {
        List<T> list = new ArrayList<>();
        Excel excel = (Excel) clazz.getAnnotation(Excel.class);
        if (Objects.nonNull(excel)) {
            beginRow = excel.beginRow();
        }
        for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            for (int rowIndex = beginRow; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                T it = (T) clazz.newInstance();
                Row row = sheet.getRow(rowIndex);
                if (row == null) {
                    continue;
                }
                for (int columnIndex = 0; columnIndex < row.getLastCellNum(); columnIndex++) {
                    Cell cell = row.getCell(columnIndex);
                    String value = ExcelUtil.getCellStringValue(cell);
                    if (MyStringUtils.isDecimalPointOfManyZero(value)) {
                        value = value.split("\\.")[0];
                    }
                    for (Field field: clazz.getDeclaredFields()) {
                        com.practice.easyexcel.aop.annotation.Cell cell1 = field.getAnnotation(com.practice.easyexcel.aop.annotation.Cell.class);
                        if (cell == null) {
                            continue;
                        }
                        int cn = ExcelUtil.cellTNumByCa(cell1);
                        setField(it, columnIndex, value, field, cn);
                    }
                }
                list.add(it);
            }
        }
        return  list;
    }

    private void setField(T it, int columnIndex, String value, Field field, int cn) throws IllegalAccessException {
        if (columnIndex == (cn - 1)) {
            field.setAccessible(true);
            String type = field.getGenericType().toString();
            if (type.equals("class java.lang.Integer") || type.equals("int")) {
                field.set(it, Integer.parseInt(value));
            } else if (type.equals("class java.lang.Double") || type.equals("double")) {
                field.set(it, Double.parseDouble(value));
            } else if (type.equals("class java.lang.Boolean") || type.equals("boolean")) {
                field.set(it, Boolean.parseBoolean(value));
            } else {
                field.set(it, value);
            }
        }
    }

    /**
     * 初始化参数
     *
     * @param param
     * @param clazz
     */
    private void initParam(Map<String, Object> param, Class<T> clazz) {
        //传参
        if (param != null) {
            if (Objects.nonNull(param.get(Constants.IN_FILE_PATH))) {
                inFilePath = param.get(Constants.IN_FILE_PATH).toString();
            }
            if (Objects.nonNull(param.get(Constants.BEGIN_ROW))) {
                beginRow = Integer.parseInt(param.get(Constants.BEGIN_ROW).toString());
            }
        } else {
            // 执行增强逻辑，使用注解
            Excel excel = (Excel) clazz.getAnnotation(Excel.class);
            if (Objects.nonNull(excel)) {
                inFilePath = excel.inFilePath();

            }
        }
    }

    @Override
    public List read(Class clazz) {
        return null;
    }
}

package com.practice.easyexcel.aop.read;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 读取excel
 * @author liuyazhou
 */
public interface ReadExcel<T> {

    List<T> read(Map<String, Object> param, Class clazz) throws IOException, InstantiationException, IllegalAccessException;

    List<T> read(Class clazz);
}

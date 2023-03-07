package com.practice.easyexcel.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 读取/写入excel自定义注解
 * @author liuyazhou
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Excel {

    /**
     * 从哪一行开始
      */
    int beginRow() default 1;

    /**
     * 是否自动生成序列
     */
    boolean isNeedSequence() default  false;

    /**
     * 列表头数据，格式为： 字段名：哪一列：列宽，格式为 字段名：哪一列：列宽。。。
     */
    String dataHeader() default "";

    /**
     * 生成后的路径
     */
    String outFilePath() default "";

    /**
     * 读取文件的路径
     */
    String inFilePath() default "";

    /**
     * 是否自适应高度
     */
    boolean autoHeigh() default false;

    /**
     * 创建行的方式，add新增一行，insert插入一行
     */
    String createRowWay() default "add";
}

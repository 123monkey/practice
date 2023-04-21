package com.practice.java8;

import lombok.Data;

import java.io.Serializable;

/**
 * 测试对象判断
 * @author liuyazhou
 */
@Data
public class Operation implements Serializable {

    private Long  id;

    private String op;
}

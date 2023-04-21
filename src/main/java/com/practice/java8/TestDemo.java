package com.practice.java8;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 测试java8一些特性
 * @author liuyazhou
 */
public class TestDemo {

    public static void main(String[] args) {
        String s = "123";
        s = Optional.ofNullable(s).orElse("aaa");
        System.out.println(s);
        String s1 = null;
        s1 = Optional.ofNullable(s1).orElse("aaa");
        System.out.println(s1);

        List<Long> list = new ArrayList<>();
        System.out.println(list);
        Operation operation = null;
     //   Operation operation = new Operation();
        Optional.ofNullable(operation).ifPresent(operation1 -> list.add(operation1.getId()));
        System.out.println(list);
       // System.out.println(Optional.ofNullable(operation).orElseThrow(()->new RuntimeException("当前的数据不存在")));

        List<Long> list2 = new ArrayList<>();
        Operation operation2 = new Operation();
        operation2.setId(1L);
        Optional.ofNullable(operation2).ifPresent(e -> list2.add(e.getId()));
        System.out.println(list2);



    }
}

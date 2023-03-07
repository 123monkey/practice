package com.practice.easyexcel.test;

import cn.hutool.core.collection.CollUtil;
import com.practice.easyexcel.utils.ExcelUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuyazhou
 */
@RestController
public class TestExcelController {

    /**
     * 基于占位符进行填充
     * @param response
     */
    @GetMapping("/exportTemplateOne")
    public void exportTemplateOne(HttpServletResponse response) {
        Map<String, String> map = new HashMap<>();
        // 根据name进行填充对应的
        map.put("title", "单列表多数据");
        map.put("test1", "数据测试1");
        map.put("test2", "数据测试2");
        map.put("test3", "数据测试3");
        map.put("test4", "数据测试4");
        map.put("testTest", "666");
        // 下面的列，进行填充对应的列信息 .属性
        List<TestObj> list = new ArrayList<>();
        list.add(new TestObj("单列表测试1", "列表测试1", "列表测试2", "列表测试3", "列表测试4"));
        list.add(new TestObj("单列表测试2", "列表测试5", "列表测试6", "列表测试7", "列表测试8"));
        list.add(new TestObj("单列表测试3", "列表测试9", "列表测试10", "列表测试11", "列表测试12"));
        ExcelUtil.exportTemplate(CollUtil.newArrayList(map, list), "单列表.xlsx", "单列表.xlsx", response);
    }

    /**
     * 多列表多数据
     */
    @GetMapping("/exportTemplateMuliti")
    public void exportTemplateMuliti(HttpServletResponse response) {
        // 表头信息
        Map<String, String> map = new HashMap<>();
        map.put("title1", "标题1");
        map.put("title2", "标题2");
        map.put("title3", "标题3");
        map.put("title4", "标题4");
        map.put("author", "Lion Li");
        List<TestObj1> list1 = new ArrayList<>();
        list1.add(new TestObj1("list1测试1", "list1测试2", "list1测试3"));
        list1.add(new TestObj1("list1测试4", "list1测试5", "list1测试6"));
        list1.add(new TestObj1("list1测试7", "list1测试8", "list1测试9"));
        List<TestObj1> list2 = new ArrayList<>();
        list2.add(new TestObj1("list2测试1", "list2测试2", "list2测试3"));
        list2.add(new TestObj1("list2测试4", "list2测试5", "list2测试6"));
        List<TestObj1> list3 = new ArrayList<>();
        list3.add(new TestObj1("list3测试1", "list3测试2", "list3测试3"));
        List<TestObj1> list4 = new ArrayList<>();
        list4.add(new TestObj1("list4测试1", "list4测试2", "list4测试3"));
        list4.add(new TestObj1("list4测试4", "list4测试5", "list4测试6"));
        list4.add(new TestObj1("list4测试7", "list4测试8", "list4测试9"));
        list4.add(new TestObj1("list4测试10", "list4测试11", "list4测试12"));
        // 多列
        Map<String, Object> multiListMap = new HashMap<>();
        multiListMap.put("map", map);
        multiListMap.put("data1", list1);
        multiListMap.put("data2", list2);
        multiListMap.put("data3", list3);
        multiListMap.put("data4", list4);
        ExcelUtil.exportTemplateMultiList(multiListMap, "多列表.xlsx", "多列表.xlsx", response);
    }

    /**
     * test对象 test1-test3
     */
    @Data
    @AllArgsConstructor
    static class TestObj1 {
        private String test1;
        private String test2;
        private String test3;
    }


    /**
     * test对象的信息： name、list1-list4
     */
    @Data
    @AllArgsConstructor
    static class TestObj {
        private String name;
        private String list1;
        private String list2;
        private String list3;
        private String list4;
    }

}

package com.practice.design.decorator.v1;

// 活动类
class Activity implements ActivityInterface {
    private String type;
    private Long id;
    private String name;
    private Integer scene;
    private String material;
      
    public Activity(String type) {
        this.type = type;
        // id的构建部分依赖于活动的type
        if ("period".equals(type)) {
            id = 0L;
        }
    }
    public Activity(String type, Long id) {
        this.type = type;
        this.id = id;
    }
    public Activity(String type, Long id, Integer scene) {
        this.type = type;
        this.id = id;
        this.scene = scene;
    }
    public Activity(String type, String name, Integer scene, String material) {
        this.type = type;
        this.scene = scene;
        this.material = material;
        // name的构建完全依赖于活动的type
        if ("period".equals(type)) {
            this.id = 0L;
            this.name = "period" + name;
        } else {
            this.name = "normal" + name;
        }
    }
    // 参与活动
   @Override
    public void participate(Long userId) {
        // do nothing
    }
}
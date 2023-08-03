package com.practice.design.state.v2;

/**
 * 活动业务
 */
public class ActivityService {
    // 通知完成
    public void notifyFinished(Long taskId) {
        System.out.println("this task is finish"+taskId);
    }
}

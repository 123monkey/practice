package com.practice.design.state.observer;

import com.practice.design.state.v2.ActivityService;

// 活动观察者
class ActivityObserver implements Observer {
    private ActivityService activityService;
    @Override
    public void response(Long taskId) {
        activityService.notifyFinished(taskId);
    }
}
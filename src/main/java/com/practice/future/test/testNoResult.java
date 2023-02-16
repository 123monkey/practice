package com.practice.future.test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author liuyazhou
 */
public class testNoResult {
    public static void main(String[] args) {
        CompletableFuture<Void> completedFuture = CompletableFuture.runAsync(() ->
        {
            System.out.println(Thread.currentThread().getName() + "开始调用异步任务");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + "调用异步任务结束");
        });

        System.out.println(Thread.currentThread().getName() + "正在执行主线程任务");

        try {
            completedFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}

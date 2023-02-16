package com.practice.future.test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author liuyazhou
 */
public class testHasResult {
    public static void main(String[] args) {
        CompletableFuture<Integer> completedFuture = CompletableFuture.supplyAsync(() ->
        {
            System.out.println(Thread.currentThread().getName() + "开始调用异步任务");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + "调用异步任务结束");
            return 1111;
        });

        System.out.println(Thread.currentThread().getName() + "正在执行主线程任务");

        try {
            System.out.println("异步任务的执行结果：" + completedFuture.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}

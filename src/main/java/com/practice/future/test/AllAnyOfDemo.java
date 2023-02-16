package com.practice.future.test;

import java.util.concurrent.CompletableFuture;

/**
 * @author liuyazhou
 */
public class AllAnyOfDemo {
    public static void main(String[] args) {
        CompletableFuture<String> completedFuture1 = CompletableFuture.supplyAsync(() ->
        {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("异步任务1完成");
            return "sleep1";
        });

        CompletableFuture<String> completedFuture3 = CompletableFuture.supplyAsync(() ->
        {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("异步任务3完成");
            return "sleep3";
        });

        CompletableFuture<String> completedFuture5 = CompletableFuture.supplyAsync(() ->
        {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("异步任务5完成");
            return "sleep5";
        });

        CompletableFuture<Void> completableFutureResult = CompletableFuture.anyOf(
                completedFuture1, completedFuture3, completedFuture5).thenAccept(x -> {
                    System.out.println("anyOf任务已经完成");
                }
        );

        System.out.println("主线程等待异步运算结果...");
        completableFutureResult.join();
    }
}
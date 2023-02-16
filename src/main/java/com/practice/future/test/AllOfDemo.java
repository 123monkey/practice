package com.practice.future.test;

import java.util.concurrent.CompletableFuture;

/**
 * @author liuyazhou
 */
public class AllOfDemo {
    public static void main(String[] args) {
        CompletableFuture<String> completedFuture1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("异步任务1完成");
            return "sleep1";
        });

        CompletableFuture<String> completedFuture3 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("异步任务3完成");
            return "sleep3";
        });

        CompletableFuture<String> completedFuture5 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("异步任务5完成");
            return "sleep5";
        });

        CompletableFuture<String> completableFutureResult = CompletableFuture.allOf(
                completedFuture1, completedFuture3, completedFuture5).thenApply(x -> {
                    //这里可以对结果进行处理
                    StringBuilder sb = new StringBuilder();
                    sb.append(completedFuture1.join()).append(completedFuture3.join()).append(completedFuture5.join());

                    return sb.toString();
                }
        );

        System.out.println("主线程等待异步运算结果...");
        String res = completableFutureResult.join();
        System.out.println("主线程打印异步计算结果：" + res);
    }
}

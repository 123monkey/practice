package com.practice.future.test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author liuyazhou
 */
public class whenCompleteDemo {
    public static void main(String[] args) {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int res = 10 / 2;
            // int res = 10 / 0;
            System.out.println(Thread.currentThread().getName() + ": 异步任务执行中，结果为：" + res);

            return res;
        }).whenComplete((result, ex) -> {
            if (result != null) {
                System.out.println("whenComplete: " + result);
            }

            if (ex != null) {
                System.out.println("whenComplete: " + ex);
            }
        });

        Integer res = null;
        try {
            System.out.println(Thread.currentThread().getName() + ": 主线程在做其他事情");
            res = completableFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.print(Thread.currentThread().getName() + ": " + res);
    }
}
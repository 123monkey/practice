package com.practice.future.test;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author liuyazhou
 */
public class ThenApplyDemo {
    public static void main(String[] args) {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() ->
        {
            Random random = new Random();
            Integer res = random.nextInt(100);
            System.out.println(Thread.currentThread().getName() + " supplyAsync结果: " + res + " " + res.getClass());
            return res;
         //thenApply可进行修改 thenApplyAsync
        }).thenApply((integer -> {
            String res = String.valueOf(integer);
            System.out.println(Thread.currentThread().getName() + " thenApply结果: " + res + " " + res
                    .getClass());
            return res;
        }));

        try {
            String res = completableFuture.get();
            System.out.println(Thread.currentThread().getName() + ": " + res + " " + res.getClass());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
package com.practice.future.test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author liuyazhou
 */
public class HandleDemo {
    public static void main(String[] args) {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() ->
        {
            // int res = 10 / 0;
            int res = 10 / 2;
            return res;
        }).handleAsync((res, ex) -> {
            if (res != null) {
                System.out.println(Thread.currentThread().getName() + " handle: " + res);
            }

            if (ex != null) {
                System.out.println(Thread.currentThread().getName() + " handle: " + ex);
            }

            return res;
        });

        try {
            int res = completableFuture.get();
            System.out.println(Thread.currentThread().getName() + ": " + res);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
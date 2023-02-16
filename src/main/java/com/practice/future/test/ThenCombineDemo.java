package com.practice.future.test;

import java.util.concurrent.CompletableFuture;

/**
 * @author liuyazhou
 */
public class ThenCombineDemo {
    public static void main(String[] args) {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "hello");

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "world");

        CompletableFuture<String> result = future2.thenCombine(future1, (t, u) -> t + " " + u);
        System.out.println(result.join());
    }
}
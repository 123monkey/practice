package com.practice.future.test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author liuyazhou
 */
public class GetJoinDemo {
    public static void main(String[] args) {
        CompletableFuture<Integer> completedFuture = CompletableFuture.supplyAsync(() -> {
            int res = 10 / 2;
            return res;
        });

        // join抛出的unchecked异常不需要代码中处理
        int resJoin = completedFuture.join();
        System.out.println(resJoin);

        // get抛出的是受查异常，需要向上throws或者try-catch包围
        int resGet = 0;
        try {
            resGet = completedFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(resGet);
    }
}
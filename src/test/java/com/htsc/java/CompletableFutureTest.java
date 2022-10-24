package com.htsc.java;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class CompletableFutureTest {
    public static void main(String[] args) {
        long t1 = System.currentTimeMillis();
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(()-> getString(), Executors.newSingleThreadExecutor());
        System.out.println(completableFuture.join() + "t1");
        System.out.println(11111111);
    }

    private static Integer getString() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Random().nextInt(1000);
    }
}

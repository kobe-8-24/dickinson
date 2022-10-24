package com.htsc.java;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class LambdaTest {
    public static void main(String[] args) {
        List<Integer> integerList = Arrays.asList(1,2,3,4,5,6,7,8,9);
        integerList
                .parallelStream()
                .peek(s -> System.out.println("线程：" + Thread.currentThread().getName()))
                .filter(s -> s > 3)
                .map(s -> s + 1)
                .forEach(System.out::println);
    }
}

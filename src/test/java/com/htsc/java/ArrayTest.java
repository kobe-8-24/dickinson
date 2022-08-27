package com.htsc.java;

import java.util.Arrays;
import java.util.OptionalDouble;

public class ArrayTest {
    public static void main(String[] args) {
        int[] prime = {2, 3, 4};
        OptionalDouble average = Arrays.stream(prime).average();
        System.out.println(average.getAsDouble());

        int[] primeCopy = Arrays.copyOf(prime, prime.length);

        primeCopy[2] = 6;

        for (int single:primeCopy) {
            System.out.println(single);
        }

        for (int s:prime) {
            System.out.println(s);
        }


    }
}

package com.htsc.java;

public class AssertTest {
    public static void main(String[] args) {
        System.out.println(calculate(-3));
    }


    private static double calculate(int x) {
//        if (x < 0) {
//            throw new IllegalArgumentException("x < 0");
//        }

        assert x > 0 : x;

        double sqrt = Math.sqrt(x);
        return sqrt;
    }
}

package com.htsc.java;

public class StringTest {
    public static void main(String[] args) {
        System.out.println(getFormat("dickinson", 24));
    }

    private static String getFormat(String name, int age) {
        return String.format("hello, %s .next year, you'll be %d ", name, age);
    }
}

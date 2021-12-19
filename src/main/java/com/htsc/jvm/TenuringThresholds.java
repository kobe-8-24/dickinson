package com.htsc.jvm;

public class TenuringThresholds {
    public static void main(String[] args) {
        // allocate 4M space
        byte[] b = new byte[10 * 1024 * 1024];
        System.out.println("first allocate");
        // allocate 4M space
        b = new byte[10 * 1024 * 1024];
        System.out.println("second allocate");
    }
}

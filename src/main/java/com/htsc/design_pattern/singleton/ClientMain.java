package com.htsc.design_pattern.singleton;

public class ClientMain {
    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            SingletonLazy singletonDemo = SingletonLazy.getInstance();
            System.out.println(singletonDemo);
        }
    }
}

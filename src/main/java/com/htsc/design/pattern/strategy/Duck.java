package com.htsc.design.pattern.strategy;

public abstract class Duck {
    public Duck() {

    }

    public abstract void display(); // 显示鸭子的信息

    public void quack() {
        System.out.println("鸭子嘎嘎嘎");
    }

    public void swim() {
        System.out.println("鸭子会游泳");
    }

    public void fly() {
        System.out.println("鸭子会飞翔");
    }
}

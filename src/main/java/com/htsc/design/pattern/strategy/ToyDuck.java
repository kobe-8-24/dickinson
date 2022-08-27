package com.htsc.design.pattern.strategy;

public class ToyDuck extends Duck {
    @Override
    public void display() {
        System.out.println("this is 玩具鸭！！！");
    }

    // 需要重写父类所有方法
    public void quack() {
        System.out.println("鸭子不会嘎嘎嘎");
    }

    public void swim() {
        System.out.println("鸭子不会游泳");
    }

    public void fly() {
        System.out.println("鸭子不会飞翔");
    }
}

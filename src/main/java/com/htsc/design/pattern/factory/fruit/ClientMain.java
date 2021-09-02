package com.htsc.design.pattern.factory.fruit;

public class ClientMain {
    public static void main(String[] args) {
        //创建苹果工厂
        FruitFactory appleFactory = new AppleFactory();
        //通过苹果工厂生产苹果实例
        Fruit apple = appleFactory.getFruit();
        apple.get();

        //创建香蕉工厂
        FruitFactory bananaFactory = new BananaFactory();
        //通过香蕉工厂生产香蕉实例
        Fruit banana = bananaFactory.getFruit();
        banana.get();
    }
}

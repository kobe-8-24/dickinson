package com.htsc.design.pattern.factory.fruit;

public class AppleFactory implements FruitFactory {
    @Override
    public Fruit getFruit() {
        return new Apple();
    }
}

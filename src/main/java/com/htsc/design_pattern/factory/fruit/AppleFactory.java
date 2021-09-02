package com.htsc.design_pattern.factory.fruit;

public class AppleFactory implements FruitFactory {
    @Override
    public Fruit getFruit() {
        return new Apple();
    }
}

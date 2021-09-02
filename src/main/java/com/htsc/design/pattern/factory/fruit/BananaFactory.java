package com.htsc.design.pattern.factory.fruit;

public class BananaFactory implements FruitFactory {
    @Override
    public Fruit getFruit() {
        return new Banana();
    }
}

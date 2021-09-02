package com.htsc.design_pattern.factory.fruit;

public class BananaFactory implements FruitFactory {
    @Override
    public Fruit getFruit() {
        return new Banana();
    }
}

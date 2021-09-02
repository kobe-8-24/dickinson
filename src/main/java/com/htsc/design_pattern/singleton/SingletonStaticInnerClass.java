package com.htsc.design_pattern.singleton;

public class SingletonStaticInnerClass {

    public static SingletonStaticInnerClass getInstance() {
        return Inner.instance;
    }

    private SingletonStaticInnerClass() {

    }

    private static class Inner {
        private static final SingletonStaticInnerClass instance = new SingletonStaticInnerClass();
    }
}

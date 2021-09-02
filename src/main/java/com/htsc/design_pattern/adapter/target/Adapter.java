package com.htsc.design_pattern.adapter.target;

/**
 * 适配器类，继承了被适配类，同时实现标准接口
 */
public class Adapter extends Adaptee implements Target {
    @Override
    public void request() {
        super.specificRequest();
    }
}

package com.htsc.design_pattern.adapter.object;

import com.htsc.design_pattern.adapter.target.Adaptee;
import com.htsc.design_pattern.adapter.target.Target;

/**
 * 适配器类，直接关联被适配类，同时实现标准接口
 */
class AdapterObject implements Target {
    private Adaptee adaptee;

    AdapterObject(Adaptee adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void request() {
        this.adaptee.specificRequest();
    }
}

package com.htsc.design.pattern.adapter.object;

import com.htsc.design.pattern.adapter.target.Adaptee;
import com.htsc.design.pattern.adapter.target.ConcreteTarget;
import com.htsc.design.pattern.adapter.target.Target;

public class ClientObjectMain {
    public static void main(String[] args) {
        Target concreteTarget = new ConcreteTarget();
        concreteTarget.request();

        Target adapterObject = new AdapterObject(new Adaptee());
        adapterObject.request();
    }
}

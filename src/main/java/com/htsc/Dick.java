package com.htsc;

import com.htsc.design.pattern.factory.fruit.Apple;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Dick {

@Bean    public Apple getApple() {
        System.out.println("wo jia zai le Apple!!!!!");
        return new Apple();
    }
}

package com.htsc.design.pattern.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * hello world
 */
@RestController
public class HelloWorld {

    /**
     * method get_mapping
     * @return hello
     */
    @GetMapping("/hello")
    public String sayHello() {
        return "hello dickinson";
    }
}

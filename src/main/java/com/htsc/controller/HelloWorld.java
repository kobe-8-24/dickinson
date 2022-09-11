package com.htsc.controller;

import com.htsc.mapper.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * hello world
 */
@RestController
public class HelloWorld {

    @Autowired
    private UserMapper userMapper;

    /**
     * method get_mapping
     * @return hello
     */
    @GetMapping("/hello")
    public String sayHello() {
        return userMapper.getUserNameByUserId(1);
    }
}

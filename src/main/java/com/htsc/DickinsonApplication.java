package com.htsc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.htsc.mapper.dao")
public class DickinsonApplication {
    public static void main(String[] args) {
        SpringApplication.run(DickinsonApplication.class, args);
    }

}

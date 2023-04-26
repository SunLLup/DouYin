package com.example.springbootdouy;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.springbootdouy.mapper")

public class SpringbootDouyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDouyApplication.class, args);
    }

}

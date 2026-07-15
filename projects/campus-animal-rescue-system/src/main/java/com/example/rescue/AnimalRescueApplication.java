package com.example.rescue;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.rescue.mapper")
@SpringBootApplication
public class AnimalRescueApplication {
    public static void main(String[] args) {
        SpringApplication.run(AnimalRescueApplication.class, args);
    }
}

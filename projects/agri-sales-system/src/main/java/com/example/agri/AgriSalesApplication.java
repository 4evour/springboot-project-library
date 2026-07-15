package com.example.agri;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.agri.mapper")
@SpringBootApplication
public class AgriSalesApplication {
    public static void main(String[] args) {
        SpringApplication.run(AgriSalesApplication.class, args);
    }
}

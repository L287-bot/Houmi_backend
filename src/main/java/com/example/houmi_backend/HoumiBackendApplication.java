package com.example.houmi_backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.houmi_backend.mapper")
public class HoumiBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(HoumiBackendApplication.class, args);
    }

}

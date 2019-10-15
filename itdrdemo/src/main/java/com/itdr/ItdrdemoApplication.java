package com.itdr;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.itdr.mappers")
public class ItdrdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItdrdemoApplication.class, args);
    }

}

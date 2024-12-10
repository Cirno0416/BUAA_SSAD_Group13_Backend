package com.innoshare;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.innoshare.mapper")
@EnableScheduling
public class InnoApplication {
    public static void main(String[] args) {
        SpringApplication.run(InnoApplication.class, args);
    }

}

package com.innoshare;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.innoshare.mapper")
public class InnoApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(InnoApplication.class, args);
    }

}

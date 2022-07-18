package com.zufang.app.upm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.zufang.app.upm.mapper")
public class UpmApplication {

    public static void main(String[] args) {
        SpringApplication.run(UpmApplication.class, args);
    }

}

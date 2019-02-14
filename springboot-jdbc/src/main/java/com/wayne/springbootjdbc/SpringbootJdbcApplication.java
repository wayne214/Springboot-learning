package com.wayne.springbootjdbc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wayne.springbootjdbc.mapper")
public class SpringbootJdbcApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootJdbcApplication.class, args);
    }

}


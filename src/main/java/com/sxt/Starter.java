package com.sxt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.sxt")//扫描
@EnableScheduling
public class Starter {
    public static void main( String[] args ) {

        SpringApplication.run(Starter.class);
    }
}

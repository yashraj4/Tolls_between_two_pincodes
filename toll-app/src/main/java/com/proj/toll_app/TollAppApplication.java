package com.proj.toll_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching 
public class TollAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(TollAppApplication.class, args);
    }
}
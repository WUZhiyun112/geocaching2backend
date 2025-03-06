package com.example.geocaching1;

import com.example.geocaching1.config.SecurityConfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@Import(SecurityConfig.class)
@SpringBootApplication
@ComponentScan(basePackages = {"com.example.geocaching1", "com.example.geocaching1.config"})

public class BackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }
}

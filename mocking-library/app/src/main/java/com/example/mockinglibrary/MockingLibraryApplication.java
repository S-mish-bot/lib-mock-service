package com.example.mockinglibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.mockinglibrary")
public class MockingLibraryApplication {
    public static void main(String[] args) {
        SpringApplication.run(MockingLibraryApplication.class, args);
    }
}

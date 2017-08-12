package com.jd.tinkerpop.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.jd.tinkerpop.*"})
public class ExerciseApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(ExerciseApplication.class, args);
    }
}
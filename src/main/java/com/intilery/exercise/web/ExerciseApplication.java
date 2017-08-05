package com.intilery.exercise.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.intilery.*"})
public class ExerciseApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(ExerciseApplication.class, args);
    }
}
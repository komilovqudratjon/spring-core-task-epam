package com.epam.upskill.springcore;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
public class SpringCoreTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCoreTaskApplication.class, args);
    }

}

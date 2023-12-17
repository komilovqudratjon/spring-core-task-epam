package com.epam.upskill.springcore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * Configures Swagger for API documentation.
 */
//@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private static final String USERNAME = "https://t.me/qudratjon03031999";

    private ApiInfo apiInfo() {
        return new ApiInfo("Gym CRM System",
                "Gym CRM System api documentation... CREATED BY Qudratjon Komilov",
                "1.0",
                USERNAME,
                new Contact("Team", USERNAME, "komilovqudratjon@gmail.com"),
                "Api deside youtube video",
                USERNAME,
                Collections.emptyList());
    }


}
package com.internship.books_api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Books API")
                        .version("1.0")
                        .description("REST API for managing books with JWT authentication and role-based authorization")
                        .contact(new Contact()
                                .name("Anas")
                                .email("anas@example.com")));
    }
}
package com.tomasherrado.sl.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI slOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("SL - Supermarket List API")
                        .description("API para administrar una lista de supermercado compartida")
                        .version("v0.0.1")
                        .contact(new Contact()
                                .name("Tomas Herrado")));
    }
}
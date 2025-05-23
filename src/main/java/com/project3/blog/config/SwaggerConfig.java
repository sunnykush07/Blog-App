package com.project3.blog.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Blog API : Backend Project")
                .description("This API provides functionalities for a blogging platform, including user authentication, post management, and comments.")
                .version("1.0.0")
                .contact(new Contact()
                    .name("Sunny Kushwaha")
                    .email("sunny@example.com")
                    .url("https://github.com/sunnyksh07"))
                .license(new License()
                    .name("Apache 2.0")
                    .url("https://www.apache.org/licenses/LICENSE-2.0.html"))
            )
            .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
            .components(new io.swagger.v3.oas.models.Components()
                .addSecuritySchemes("BearerAuth", new SecurityScheme()
                    .name("BearerAuth")
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
                )
            );
    }
}

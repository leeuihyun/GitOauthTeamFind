package com.hyun.oauthboard.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(
        title = "Github Oauth",
        description = "Github Oauth 팀찾기 프로젝트",
        version = "v1"
    )
)

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI openAPI() {
        SecurityScheme bearerAuth = new SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT");

        SecurityRequirement securityRequirement = new SecurityRequirement()
            .addList("BearerAuth");

        return new OpenAPI()
            .components(new Components().addSecuritySchemes("BearerAuth", bearerAuth))
            .addSecurityItem(securityRequirement);
    }
}


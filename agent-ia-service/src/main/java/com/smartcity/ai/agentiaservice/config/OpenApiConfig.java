package com.smartcity.ai.agentiaservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Agent IA Service API")
                        .version("1.0.0")
                        .description("Service d'Agent Intelligent pour la Smart City utilisant Spring AI et Ollama. " +
                                "Cet agent peut interroger les services Traffic et Energy pour répondre aux questions des citoyens.")
                        .contact(new Contact()
                                .name("Smart City Team")
                                .email("support@smartcity.ai"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .components(new Components()
                        .addSecuritySchemes("bearer-jwt", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Entrez le token JWT (obtenu depuis /auth/login). " +
                                        "Note: Les endpoints /agent/chat et /agent/stream sont publics et ne nécessitent PAS de token.")));
    }
}

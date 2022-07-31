package com.tungstun.core.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.SpecVersion;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI gatewayOpenApi(@Value("${GATEWAY_URL:}") String gatewayUrl) {
        OpenAPI openApi = new OpenAPI(SpecVersion.V31)
                .info(new Info()
                        .title("bartap Backend Api - Core")
                        .description("Core API of the bartap Backend API microservice cluster containing bar and session functionality")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Tungstun")
                                .url("https://github.com/tungstun-ict")
                                .email("jort@tungstun.nl")))
                .addTagsItem(new Tag().name("Bar").description("Functionality based around the Bar"))
                .addTagsItem(new Tag().name("Session").description("Functionality based around the Session"))
                .schemaRequirement("Bearer", new SecurityScheme()
                        .name("Bearer")
                        .description("Authorization using Bearer JWT")
                        .type(SecurityScheme.Type.HTTP)
                        .in(SecurityScheme.In.HEADER)
                        .scheme("bearer")
                        .bearerFormat("JWT"));

        if (gatewayUrl != null) {
            openApi.addServersItem(new Server()
                    .description("Gateway Url")
                    .url(gatewayUrl)
            );
        }

        return openApi;
    }
}

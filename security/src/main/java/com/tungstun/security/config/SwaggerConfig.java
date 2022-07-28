package com.tungstun.security.config;

import io.swagger.v3.oas.annotations.OpenAPI31;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;

@OpenAPI31
@OpenAPIDefinition(
        info = @Info(
                title = "bartap Backend Api - Security",
                description = "Security API of the bartap Backend API microservice cluster containing user and authorization functionality",
                version = "1.0",
                contact = @Contact(
                        name = "Tungstun",
                        url = "https://github.com/tungstun-ict",
                        email = "jort@tungstun.nl"
                )
        ),
        tags = {
                @Tag(name = "User", description = "Functionality based around the user"),
                @Tag(name = "Authentication", description = "Functionality based around the authentication"),
                @Tag(name = "Authorization", description = "Functionality based around the authorization"),
        }
)
public class SwaggerConfig {
}

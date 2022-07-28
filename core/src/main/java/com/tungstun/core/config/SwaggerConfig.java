package com.tungstun.core.config;

import io.swagger.v3.oas.annotations.OpenAPI31;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;

@OpenAPI31
@OpenAPIDefinition(
        info = @Info(
                title = "bartap Backend Api - Core",
                description = "Core API of the bartap Backend API microservice cluster containing bar and session functionality",
                version = "1.0",
                contact = @Contact(
                        name = "Tungstun",
                        url = "https://github.com/tungstun-ict",
                        email = "jort@tungstun.nl"
                )
        ),
        tags = {
                @Tag(name = "Bar", description = "Functionality based around the Bar"),
                @Tag(name = "Session", description = "Functionality based around the Session")
        }
)
public class SwaggerConfig {
}

package com.tungstun.product.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.annotations.OpenAPI31;

@OpenAPI31
@OpenAPIDefinition(
        info = @Info(
                title = "bartap Backend Api - Product",
                description = "Core API of the bartap Backend API microservice cluster containing product and category functionality",
                version = "1.0",
                contact = @Contact(
                        name = "Tungstun",
                        url = "https://github.com/tungstun-ict",
                        email = "jort@tungstun.nl"
                )
        ),
        tags = {
                @Tag(name = "Category", description = "Functionality based around the Category"),
                @Tag(name = "Product", description = "Functionality based around the Product")
        }
)
public class SwaggerConfig {
}

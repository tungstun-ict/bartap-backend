package com.tungstun.bill.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.annotations.OpenAPI31;

@OpenAPI31
@OpenAPIDefinition(
        info = @Info(
                title = "bartap Backend Api - Bill",
                description = "Bill API of the bartap Backend API microservice cluster containing bill functionality",
                version = "1.0",
                contact = @Contact(
                        name = "Tungstun",
                        url = "https://github.com/tungstun-ict",
                        email = "jort@tungstun.nl"
                )
        ),
        tags = {
                @Tag(name = "Bill", description = "Functionality based around the Bill"),
                @Tag(name = "Order", description = "Functionality based around the Order and Order History"),
        }
)
public class SwaggerConfig {
}

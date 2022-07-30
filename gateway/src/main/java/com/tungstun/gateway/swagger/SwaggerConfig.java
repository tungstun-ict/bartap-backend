package com.tungstun.gateway.swagger;

import io.swagger.v3.oas.annotations.OpenAPI31;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.apache.http.entity.ContentType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@OpenAPI31
@OpenAPIDefinition(
        info = @Info(
                title = "bartap Backend Api - Gateway",
                description = "API Gateway of the bartap Backend API microservice cluster containing Centralized API Documentations.",
                version = "1.0",
                contact = @Contact(
                        name = "Tungstun",
                        url = "https://github.com/tungstun-ict",
                        email = "jort@tungstun.nl"
                )
        )
)
@Configuration
public class SwaggerConfig {
    private final ServiceDefinitionsContext definitionContext;

    public SwaggerConfig(ServiceDefinitionsContext definitionContext) {
        this.definitionContext = definitionContext;
    }

    @Bean
    public RouterFunction<ServerResponse> serviceApiDocs() {
        return RouterFunctions.route(GET("/v3/api-docs/services/{serviceName}"), (ServerRequest req) -> {
            String service = req.pathVariable("serviceName");
            String swaggerDocs = definitionContext.getSwaggerDefinition(service);
            if (swaggerDocs == null) throw new ServiceDefinitionResourceNotFoundException();
            return ServerResponse.ok()
                    .header("Content-Type", String.valueOf(ContentType.APPLICATION_JSON))
                    .body(BodyInserters.fromValue(swaggerDocs));
        });
    }
}

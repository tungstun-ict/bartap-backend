package com.tungstun.gateway.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.SpecVersion;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.Optional;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class SwaggerConfig {
    private final ServiceDefinitionsContext definitionContext;

    public SwaggerConfig(ServiceDefinitionsContext definitionContext) {
        this.definitionContext = definitionContext;
    }

    @Bean
    public OpenAPI gatewayOpenApi(@Value("${GATEWAY_URL:}") String gatewayUrl) {
        OpenAPI openApi = new OpenAPI(SpecVersion.V31)
                .info(new Info()
                        .title("bartap Backend Api - Gateway")
                        .description("API Gateway of the bartap Backend API microservice cluster containing Centralized API Documentations.")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Tungstun")
                                .url("https://github.com/tungstun-ict")
                                .email("jort@tungstun.nl")))
                .schemaRequirement("Bearer", new SecurityScheme()
                        .name("Bearer")
                        .description("Authorization using Bearer JWT")
                        .type(SecurityScheme.Type.HTTP)
                        .in(SecurityScheme.In.HEADER)
                        .scheme("bearer")
                        .bearerFormat("JWT"));

        if (gatewayUrl != null && !gatewayUrl.isEmpty()) {
            openApi.addServersItem(new Server()
                    .description("Security service")
                    .url(gatewayUrl)
            );
        }

        return openApi;
    }

    /**
     * Router Function for Swagger UI to get the cached OpenApi Definition of running registered services
     */
    @Bean
    @Operation(hidden = true)
    public RouterFunction<ServerResponse> serviceApiDocs() {
        return RouterFunctions.route(GET("/v3/api-docs/services/{serviceName}"), (ServerRequest req) -> {
            String service = req.pathVariable("serviceName");
            String swaggerDocs = Optional.ofNullable(definitionContext.getSwaggerDefinition(service))
                    .orElseThrow(ServiceDefinitionResourceNotFoundException::new);

            return ServerResponse.ok()
                    .header("Content-Type", ContentType.APPLICATION_JSON.toString())
                    .body(BodyInserters.fromValue(swaggerDocs));
        });
    }
}

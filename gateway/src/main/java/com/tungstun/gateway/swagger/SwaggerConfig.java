package com.tungstun.gateway.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

//@OpenAPI31
//@OpenAPIDefinition(
//        info = @Info(
//                title = "bartap Backend Api - Person",
//                description = "Person API of the bartap Backend API microservice cluster containing person functionality",
//                version = "1.0",
//                contact = @Contact(
//                        name = "Tungstun",
//                        url = "https://github.com/tungstun-ict",
//                        email = "jort@tungstun.nl"
//                )
//        ),
//        tags = {
//                @Tag(name = "Person", description = "Functionality based around the Person")
//        }
//)
@Configuration
public class SwaggerConfig {

    @Bean
    public RestTemplate configureTemplate() {
        return new RestTemplate();
    }

//    @Primary
//    @Bean
//    @Lazy
//    public SwaggerResourcesProvider swaggerResourcesProvider(InMemorySwaggerResourcesProvider defaultResourcesProvider, RestTemplate temp) {
//        return () -> {
//            List<SwaggerResource> resources = new ArrayList<>(defaultResourcesProvider.get());
//            resources.clear();
//            resources.addAll(definitionContext.getSwaggerDefinitions());
//            return resources;
//        };
//    }
}

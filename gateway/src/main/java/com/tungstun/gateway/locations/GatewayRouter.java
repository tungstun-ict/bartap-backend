package com.tungstun.gateway.locations;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Component
public class GatewayRouter {
    private final LocationConfig locationConfig;

    public GatewayRouter(LocationConfig locationConfig) {
        this.locationConfig = locationConfig;
    }

    @Bean
    public RouteLocator gatewayRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("bartap-api", r -> r.path("/api/**")
                        .uri(locationConfig.bartapApiUrl))
                .route("bartap-api-swagger", r -> r.path("/swagger")
                        .uri(locationConfig.bartapSwaggerUrl))
//                .route("error", r -> r.path("/error/**")
//                        .filters(fs -> fs.retry(5))
//                        .uri("http://localhost:8080/swagger"))
//                .route("circuitbreaker", r -> r.path("http://localhost:7979")
//                        .filters(f -> f.circuitBreaker(config -> config
//                                .setName("mycmd")
//                                .setFallbackUri("forward:/fallback")))
//                        .uri("http://httpbin.org:80"))
                .build();
    }

//    @Bean
//    private Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
//        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
//                .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
//                .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(2)).build())
//                .build());
//    }
//
//
//    @RequestMapping("/fallback")
//    public Mono<String> fallback() {
//        return Mono.just("fallback");
//    }
}

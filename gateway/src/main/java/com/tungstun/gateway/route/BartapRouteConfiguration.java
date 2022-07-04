package com.tungstun.gateway.route;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Duration;


@Component
public class BartapRouteConfiguration {
    private static final int REQUEST_TIME_LIMIT = 3;

    private final RouteUriConfig routeUriConfig;

    public BartapRouteConfiguration(RouteUriConfig routeUriConfig) {
        this.routeUriConfig = routeUriConfig;
    }

    @Bean
    public RouteLocator gatewayRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("security", r -> r.path("/api/authentication**")
                        .uri(routeUriConfig.getSecurity()))

                .route("core", r -> r.path("/api/bars**")
                        .uri(routeUriConfig.getCore()))

                .route("session", r -> r.path("/api/session**")
                        .uri(routeUriConfig.getCore()))

                .route("bill", r -> r.path("/api/bills**")
                        .uri(routeUriConfig.getBill()))

                .route("person", r -> r.path("/api/people**")
                        .uri(routeUriConfig.getPerson()))

                .route("product", r -> r.path("/api/products**")
                        .uri(routeUriConfig.getProduct()))
                .route("category", r -> r.path("/api/categories**")
                        .uri(routeUriConfig.getProduct()))

                .route("order", r -> r.path("/api/order**")
                        .uri(routeUriConfig.getOrder()))





//                        .filters(filter -> filter
//                                .retry(3)
//                                .circuitBreaker(factory -> factory
//                                        .setName("bartap-circuit-breaker")
//                                        .setFallbackUri("forward:/bartap-fallback"))
//                        )
//                .route("bartap-api-swagger", r -> r.path("/swagger")
//                        .uri(routeUriConfig.getSwagger()))

//                .route("error", r -> r.path("/error/**")
//                        .filters(fs -> fs.retry(5))
//                        .uri("http://localhost:8080/swagger")))

                .build();
    }

    @Bean
    private Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .circuitBreakerConfig(CircuitBreakerConfig.custom()
                        .slidingWindowSize(10)
                        .permittedNumberOfCallsInHalfOpenState(3)
                        .failureRateThreshold(50)
                        .waitDurationInOpenState(Duration.ofSeconds(30))
                        .build())
                .timeLimiterConfig(TimeLimiterConfig.custom()
                        .timeoutDuration(Duration.ofSeconds(REQUEST_TIME_LIMIT))
                        .build())
                .build());
    }
}

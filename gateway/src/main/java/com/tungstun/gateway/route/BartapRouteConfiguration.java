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
    private static final int REQUEST_RETRIES = 3;
    private static final int REQUEST_TIME_LIMIT = 3;

    private final RouteUriConfig routeUriConfig;

    public BartapRouteConfiguration(RouteUriConfig routeUriConfig) {
        this.routeUriConfig = routeUriConfig;
    }

    @Bean
    public RouteLocator gatewayRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("bartap-api", r -> r.path("/api/**")
                        .filters(filter -> filter
                                .retry(REQUEST_RETRIES)
                                .circuitBreaker(factory -> factory
                                        .setName("bartap-circuit-breaker")
                                        .setFallbackUri("forward:/bartap-fallback"))
                        )
                        .uri(routeUriConfig.getBartap()))
                .route("bartap-api-swagger", r -> r.path("/swagger")
                        .uri(routeUriConfig.getSwagger()))

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

package com.tungstun.gateway.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

@Configuration
public class FallbackConfiguration {

    @Bean
    public RouterFunction<ServerResponse> fallbackRouterFunctions() {
        return RouterFunctions.route(RequestPredicates.path("/bartap-fallback"), this::handleBartapFallback);
    }

    public Mono<ServerResponse> handleBartapFallback(ServerRequest request) {
        return ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }
}

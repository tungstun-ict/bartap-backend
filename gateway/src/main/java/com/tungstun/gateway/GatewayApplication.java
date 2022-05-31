package com.tungstun.gateway;

import com.tungstun.gateway.route.RouteUriConfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;


@SpringBootApplication
@EnableConfigurationProperties(RouteUriConfig.class)
@EnableWebFluxSecurity
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}

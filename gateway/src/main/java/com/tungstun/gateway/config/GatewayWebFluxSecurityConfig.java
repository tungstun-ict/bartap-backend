package com.tungstun.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

@Configuration
@EnableWebFluxSecurity
public class GatewayWebFluxSecurityConfig {
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http.httpBasic().and()
                .cors().and()
                .csrf().disable()
                .authorizeExchange()
                .anyExchange().permitAll()
                .and().securityContextRepository(NoOpServerSecurityContextRepository.getInstance()).build();
    }
}

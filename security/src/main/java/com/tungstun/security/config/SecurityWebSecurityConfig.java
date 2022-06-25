package com.tungstun.security.config;

import com.tungstun.sharedlibrary.security.filter.JwtAuthenticationFilter;
import com.tungstun.sharedlibrary.security.jwt.JwtValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@Order(990)
public class SecurityWebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtValidator validator;
    private final String[] ignoredPaths = {
            "/api/authentication/register",
            "/api/authentication/login",
            "/api/authentication/refresh",
            "/api/authentication/verify",
    };

    public SecurityWebSecurityConfig(JwtValidator validator) {
        this.validator = validator;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and()
                .cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, ignoredPaths).permitAll()
                .anyRequest().permitAll()
                .and()
                .addFilter(new JwtAuthenticationFilter(
                        this.authenticationManager(),
                        validator,
                        ignoredPaths
                ))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(5);
    }
}

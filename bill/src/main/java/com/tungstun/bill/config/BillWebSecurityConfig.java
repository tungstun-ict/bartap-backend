package com.tungstun.bill.config;

import com.tungstun.common.security.filter.JwtAuthenticationFilter;
import com.tungstun.common.security.jwt.JwtValidator;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@Order(999)
@Primary
public class BillWebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtValidator validator;
    private final String[] ignoredPaths = {
            "/api/swagger-ui/**",
            "/api/v3/api-docs/**",
            "/api/v3/api-docs/swagger-config"
    };

    public BillWebSecurityConfig(JwtValidator validator) {
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
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}

package com.tungstun.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@Order(1000)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and()
                .csrf().disable().authorizeRequests()
                .anyRequest().permitAll();
    }


//    private JwtAuthenticationConverter jwtAuthenticationConverter() {
//        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
//        // Convert realm_access.roles claims to granted authorities, for use in access decisions
//        converter.setJwtGrantedAuthoritiesConverter(new KeycloakRealmRoleConverter());
//        return converter;
//    }

//    class KeycloakRealmRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
//        @Override
//        public Collection<GrantedAuthority> convert(Jwt jwt) {
//            final Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");
//            return ((List<String>) realmAccess.get("roles")).stream()
//                    .map(roleName -> "ROLE_" + roleName)
//                    .map(SimpleGrantedAuthority::new)
//                    .collect(Collectors.toList());
//        }
//
//        @Override
//        public JavaType getInputType(TypeFactory typeFactory) {
//            return null;
//        }
//
//        @Override
//        public JavaType getOutputType(TypeFactory typeFactory) {
//            return null;
//        }
//
//    }
}

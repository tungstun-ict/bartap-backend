package com.tungstun.gateway.route;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and()
                    .csrf().disable().authorizeRequests()
                    .antMatchers("/swagger").permitAll()
                .antMatchers("/api/**").permitAll()
//                    .antMatchers("/authentication").permitAll()
//                .antMatchers("/api/bar/**").hasAnyRole("OWNER", "BARTENDER")
//                .antMatchers("/api/bar/*").hasAnyRole("OWNER")
//                .anyRequest().authenticated();
//        http.addFilter(new SecurityAuthorizationFilter())
                .anyRequest().permitAll();
//                    .and()
//                    .build();
        super.configure(http);
    }


//        @Bean
//        SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http,
//                                                    ReactiveClientRegistrationRepository clientRegistrationRepository) {
//            http.oauth2Login();
//            http.logout(logout -> logout.logoutSuccessHandler(
//                    new OidcClientInitiatedServerLogoutSuccessHandler(clientRegistrationRepository)));
//            http.authorizeExchange()
//                    .pathMatchers("/swagger", "/authentication").permitAll()
//                    .pathMatchers("/api/**").authenticated();
//            http.headers().frameOptions().mode(XFrameOptionsServerHttpHeadersWriter.Mode.SAMEORIGIN);
//            http.csrf().disable();
//            http.cors().disable();
//            return http.build();
//
////            http.oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter());
////            return http.httpBasic().and()
////                    .csrf().disable()
////                    .authorizeExchange()
////                    .pathMatchers("/swagger").permitAll()
////                    .pathMatchers("/authentication").permitAll()
////                    .pathMatchers("/api/**").authenticated()
//////                .anyExchange().permitAll()
////                    .and()
////                    .build();
//        }


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

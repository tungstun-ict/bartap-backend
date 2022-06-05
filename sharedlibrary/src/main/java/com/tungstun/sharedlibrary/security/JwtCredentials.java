package com.tungstun.sharedlibrary.security;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtCredentials {
    @Value("${com.tungstun.bartap.security.jwt.jwtsecret}")
    private String jwtSecret;

    @Value("${com.tungstun.bartap.security.jwt.jwtExpirationInMs}")
    private Integer jwtExpirationInMs;

    @Value("${com.tungstun.bartap.security.jwt.jwtRefreshExpirationInMs}")
    private Integer jwtRefreshExpirationInMs;

    @Value("${com.tungstun.bartap.security.jwt.jwtAudience}")
    private String jwtAudience;

    @Value("${com.tungstun.bartap.security.jwt.jwtIssuer}")
    private String jwtIssuer;

    public Algorithm algorithm() {
        return Algorithm.HMAC256(jwtSecret);
    }

    public String getJwtSecret() {
        return jwtSecret;
    }

    public Integer getJwtExpirationInMs() {
        return jwtExpirationInMs;
    }

    public Integer getJwtRefreshExpirationInMs() {
        return jwtRefreshExpirationInMs;
    }

    public String getJwtAudience() {
        return jwtAudience;
    }

    public String getJwtIssuer() {
        return jwtIssuer;
    }
}

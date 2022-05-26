package com.tungstun.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtConfiguration {
    @Value("${security.jwt.secret}")
    protected String jwtSecret;

    @Value("${security.jwt.expiration-in-ms}")
    protected Integer jwtExpirationInMs;

    @Value("${security.jwt.refresh-secret}")
    protected String jwtRefreshSecret;

    @Value("${security.jwt.refresh-expiration-in-ms}")
    protected Integer jwtRefreshExpirationInMs;

    @Value("${JWT_AUDIENCE}")
    protected String jwtAudience;

    @Value("${JWT_ISSUER}")
    protected String jwtIssuer;
}

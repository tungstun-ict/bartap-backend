package com.tungstun.sharedlibrary.security.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "com.tungstun.bartap.security.jwt")
public class JwtCredentials {
    private String jwtSecret;
    private Integer jwtExpirationInMs;
    private Integer jwtRefreshExpirationInMs;
    private String jwtAudience;
    private String jwtIssuer;

    public void setJwtSecret(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    public void setJwtExpirationInMs(Integer jwtExpirationInMs) {
        this.jwtExpirationInMs = jwtExpirationInMs;
    }

    public void setJwtRefreshExpirationInMs(Integer jwtRefreshExpirationInMs) {
        this.jwtRefreshExpirationInMs = jwtRefreshExpirationInMs;
    }

    public void setJwtAudience(String jwtAudience) {
        this.jwtAudience = jwtAudience;
    }

    public void setJwtIssuer(String jwtIssuer) {
        this.jwtIssuer = jwtIssuer;
    }

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

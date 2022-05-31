package com.tungstun.security.domain.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "com.tungstun.bartap.security.jwt")
public class JwtCredentials {
    protected String jwtSecret;

    protected Integer jwtExpirationInMs;

    protected Integer jwtRefreshExpirationInMs;

    protected String jwtAudience;

    protected String jwtIssuer;

    Algorithm algorithm() {
        return Algorithm.HMAC256(jwtSecret);
    }

//    @Override
//    public String toString() {
//        return "JwtCredentials{" +
//                "jwtSecret='" + jwtSecret + '\'' +
//                ", jwtExpirationInMs=" + jwtExpirationInMs +
//                ", jwtRefreshExpirationInMs=" + jwtRefreshExpirationInMs +
//                ", jwtAudience='" + jwtAudience + '\'' +
//                ", jwtIssuer='" + jwtIssuer + '\'' +
//                '}';
//    }
}

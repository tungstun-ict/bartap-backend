package com.tungstun.security.domain.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenGenerator {
    private final JwtCredentials credentials;

    public JwtTokenGenerator(JwtCredentials credentials) {
        this.credentials = credentials;
    }

    public String createAccessToken(String username) {
        try {
            return JWT.create()
                    .withIssuer(credentials.jwtIssuer)
                    .withAudience(credentials.jwtAudience)
                    .withExpiresAt(new Date(System.currentTimeMillis() + credentials.jwtExpirationInMs))
                    .withSubject(username)
                    .sign(credentials.algorithm());
        } catch (JWTCreationException e) {
            throw new JWTCreationException("Exception occurred during creation of a JWT", e);
        }
    }

    public String createRefreshToken() {
        try {
            return JWT.create()
                    .withIssuer(credentials.jwtIssuer)
                    .withAudience(credentials.jwtAudience)
                    .withExpiresAt(new Date(System.currentTimeMillis() + credentials.jwtRefreshExpirationInMs))
                    .sign(credentials.algorithm());
        } catch (JWTCreationException e) {
            throw new JWTCreationException("Exception occurred during creation of a JWT", e);
        }
    }
}

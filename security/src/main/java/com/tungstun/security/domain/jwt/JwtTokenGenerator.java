package com.tungstun.security.domain.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.tungstun.security.domain.user.User;
import com.tungstun.sharedlibrary.security.jwt.JwtCredentials;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenGenerator {
    private final JwtCredentials credentials;

    public JwtTokenGenerator(JwtCredentials credentials) {
        this.credentials = credentials;
    }

    public String createAccessToken(User user) {
        try {
            return JWT.create()
                    .withIssuer(credentials.getJwtIssuer())
                    .withAudience(credentials.getJwtAudience())
                    .withExpiresAt(new Date(System.currentTimeMillis() + credentials.getJwtExpirationInMs()))
                    .withSubject(user.getUsername())
                    .withClaim("client_id", user.getId())
                    .withClaim("authorizations", user.getAuthorizations())
                    .sign(credentials.algorithm());
        } catch (JWTCreationException e) {
            throw new JWTCreationException("Exception occurred during the creation of an access token", e);
        }
    }

    public String createRefreshToken() {
        try {
            return JWT.create()
                    .withIssuer(credentials.getJwtIssuer())
                    .withAudience(credentials.getJwtAudience())
                    .withExpiresAt(new Date(System.currentTimeMillis() + credentials.getJwtRefreshExpirationInMs()))
                    .sign(credentials.algorithm());
        } catch (JWTCreationException e) {
            throw new JWTCreationException("Exception occurred during the creation of th refresh token", e);
        }
    }
}

package com.tungstun.common.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tungstun.common.security.exception.NotAuthenticatedException;
import org.springframework.stereotype.Component;

@Component
public class JwtValidator {
    private final JwtCredentials credentials;

    public JwtValidator(JwtCredentials credentials) {
        this.credentials = credentials;
    }

    private JWTVerifier getJwtVerifier() {
        return JWT.require(credentials.algorithm())
                .withIssuer(credentials.getJwtIssuer())
                .withAudience(credentials.getJwtAudience())
                .withClaimPresence("client_id")
                .withClaimPresence("authorizations")
                .acceptLeeway(1)
                .build();
    }

    public DecodedJWT verifyToken(String token) {
        if(token == null) {
            throw new NotAuthenticatedException("Invalid token");
        }

        try {
            return getJwtVerifier().verify(token);
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            throw new NotAuthenticatedException("Invalid token", e);
        }
    }
}

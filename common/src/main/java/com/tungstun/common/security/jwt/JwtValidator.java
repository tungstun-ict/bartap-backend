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

    private DecodedJWT verify(String token, JWTVerifier verifier) {
        if (token == null) {
            throw new NotAuthenticatedException("Invalid token");
        }

        try {
            return verifier.verify(token);
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            throw new NotAuthenticatedException("Invalid token", e);
        }
    }

    public DecodedJWT verifyAccessToken(String token) {
        JWTVerifier verifier = JWT.require(credentials.algorithm())
                .withIssuer(credentials.getJwtIssuer())
                .withAudience(credentials.getJwtAudience())
                .withClaimPresence("client_id")
                .withClaimPresence("authorizations")
                .acceptLeeway(1)
                .build();
        return verify(token, verifier);
    }

    public DecodedJWT verifyRefreshToken(String token) {
        JWTVerifier verifier = JWT.require(credentials.algorithm())
                .withIssuer(credentials.getJwtIssuer())
                .withAudience(credentials.getJwtAudience())
                .acceptLeeway(1)
                .build();
        return verify(token, verifier);
    }
}

package com.tungstun.security.domain.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

@Component
public class JwtValidator {
    private final JwtCredentials credentials;

    public JwtValidator(JwtCredentials credentials) {
        this.credentials = credentials;
    }

    private JWTVerifier getJwtVerifier() {
        return JWT.require(credentials.algorithm())
                .withIssuer(credentials.jwtIssuer)
                .withAudience(credentials.jwtAudience)
                .withClaimPresence("sub")
                .acceptLeeway(1)
                .build();
    }

    public DecodedJWT verifyToken(String token) {
        try {
            return getJwtVerifier().verify(token);
        } catch (JWTVerificationException e){
            throw new JWTVerificationException("Jwt not verified" + e.getLocalizedMessage(), e);
        }
    }
}

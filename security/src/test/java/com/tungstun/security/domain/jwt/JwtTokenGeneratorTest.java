package com.tungstun.security.domain.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.tungstun.common.security.jwt.JwtCredentials;
import com.tungstun.common.security.jwt.JwtValidator;
import com.tungstun.security.domain.user.Authorization;
import com.tungstun.security.domain.user.Role;
import com.tungstun.security.domain.user.User;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(JwtCredentials.class)
@TestPropertySource("classpath:application.properties")
class JwtTokenGeneratorTest {
    @Autowired
    private JwtCredentials jwtCredentials;
    private JwtTokenGenerator tokenGenerator;
    private JwtValidator jwtValidator;

    @BeforeEach
    void setUp() {
        tokenGenerator = new JwtTokenGenerator(jwtCredentials);
        jwtValidator = new JwtValidator(jwtCredentials);
    }

    @Test
    void createAccessToken_ContainsCorrectValues() throws IllegalAccessException {
        User user = new User(
                "username",
                "password",
                "mail@mail.com",
                "first",
                "last",
                new ArrayList<>(List.of(new Authorization(123L, Role.OWNER))));
        FieldUtils.writeField(user, "id", 123L, true);
        long before = ZonedDateTime.now().toInstant().toEpochMilli();

        String token = tokenGenerator.createAccessToken(user);

        long after = before + jwtCredentials.getJwtExpirationInMs();
        DecodedJWT decodedJWT = jwtValidator.verifyAccessToken(token);
        assertTrue(decodedJWT.getExpiresAt().after(new Date(before)));
        assertTrue(decodedJWT.getExpiresAt().before(new Date(after)));
        assertEquals(List.of(jwtCredentials.getJwtAudience()), decodedJWT.getAudience());
        assertEquals(jwtCredentials.getJwtIssuer(), decodedJWT.getIssuer());
        assertEquals(user.getId(), decodedJWT.getClaim("client_id").asLong());
        assertEquals(user.getUsername(), decodedJWT.getSubject());
        Map<Long, String> authorizations = decodedJWT.getClaim("authorizations")
                .asMap()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(k -> Long.parseLong(k.getKey()), v -> v.getValue().toString()));
        assertEquals(user.getAuthorizations(), authorizations);
    }

    @Test
    void createRefreshToken_ContainsCorrectValues() {
        long before = ZonedDateTime.now().toInstant().toEpochMilli();

        String token = tokenGenerator.createRefreshToken();

        DecodedJWT decodedJWT = jwtValidator.verifyRefreshToken(token);
        long after = before + jwtCredentials.getJwtRefreshExpirationInMs();
        assertTrue(decodedJWT.getExpiresAt().after(new Date(before)));
        assertTrue(decodedJWT.getExpiresAt().before(new Date(after)));
        assertEquals(List.of(jwtCredentials.getJwtAudience()), decodedJWT.getAudience());
        assertEquals(jwtCredentials.getJwtIssuer(), decodedJWT.getIssuer());
    }
}
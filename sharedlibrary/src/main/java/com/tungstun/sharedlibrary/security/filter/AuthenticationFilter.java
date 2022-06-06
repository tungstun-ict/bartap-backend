package com.tungstun.sharedlibrary.security.filter;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tungstun.sharedlibrary.security.Authorization;
import com.tungstun.sharedlibrary.security.BartapUserDetails;
import com.tungstun.sharedlibrary.security.jwt.JwtValidator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AuthenticationFilter extends BasicAuthenticationFilter {
    private final JwtValidator validator;
    private final String[] ignoredPaths;

    public AuthenticationFilter(AuthenticationManager authenticationManager, JwtValidator validator, String[] ignoredPaths) {
        super(authenticationManager);
        this.validator = validator;
        this.ignoredPaths = ignoredPaths;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return Arrays.stream(ignoredPaths)
                .anyMatch(path -> path.equals(request.getRequestURI()));
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        String tokenType = request.getHeader("token_type");
        String accessToken = request.getHeader("access_token");

        if (accessToken == null || tokenType == null|| accessToken.isEmpty() || !tokenType.equals("Bearer")) {
            chain.doFilter(request, response);
        }

        try {
            DecodedJWT decodedJWT = validator.verifyToken(accessToken);
            Long userId = Optional.ofNullable(decodedJWT.getClaim("userId").asLong())
                    .orElseThrow(() -> new JWTDecodeException("No user id in access token"));
            String username = decodedJWT.getSubject();
            List<Authorization> authorizations = decodedJWT.getClaim("authorizations")
                    .asMap()
                    .entrySet()
                    .stream()
                    .map(entry -> new Authorization(entry.getKey(), (String) entry.getValue()))
                    .toList();

            BartapUserDetails principal = new BartapUserDetails(userId, username, authorizations);
            Authentication auth = new UsernamePasswordAuthenticationToken(principal, null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (JWTDecodeException ignored) {
            // Continue request without an Authentication bound to the session's request
        }
        chain.doFilter(request, response);
    }
}

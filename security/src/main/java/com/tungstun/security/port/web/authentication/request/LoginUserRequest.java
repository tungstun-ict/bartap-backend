package com.tungstun.security.port.web.authentication.request;

public record LoginUserRequest(
        String username,
        String password
) {
}

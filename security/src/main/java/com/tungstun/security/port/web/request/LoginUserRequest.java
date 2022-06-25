package com.tungstun.security.port.web.request;

public record LoginUserRequest(
        String username,
        String password
) {
}

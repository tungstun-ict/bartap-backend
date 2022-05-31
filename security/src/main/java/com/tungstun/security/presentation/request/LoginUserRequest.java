package com.tungstun.security.presentation.request;

public record LoginUserRequest(
        String username,
        String password
) {
}

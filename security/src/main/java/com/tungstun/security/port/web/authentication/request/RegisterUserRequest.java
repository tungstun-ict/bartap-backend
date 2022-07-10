package com.tungstun.security.port.web.authentication.request;

public record RegisterUserRequest(
        String username,
        String password,
        String mail,
        String firstName,
        String lastName) {
}

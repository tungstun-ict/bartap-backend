package com.tungstun.security.port.web.request;

public record RegisterUserRequest(
        String username,
        String password,
        String mail,
        String firstName,
        String lastName) {
}

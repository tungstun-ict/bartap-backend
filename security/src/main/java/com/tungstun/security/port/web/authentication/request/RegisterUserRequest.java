package com.tungstun.security.port.web.authentication.request;

public record RegisterUserRequest(
        String username,
        String password,
        String mail,
        String phoneNumber,
        String firstName,
        String lastName) {
}

package com.tungstun.security.presentation.request;

public record RegisterUserRequest(
        String username,
        String password,
        String mail,
        String firstName,
        String lastName) {
}

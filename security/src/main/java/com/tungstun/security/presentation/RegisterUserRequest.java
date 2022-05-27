package com.tungstun.security.presentation;

public record RegisterUserRequest(
        String username,
        String password,
        String firstName,
        String lastName,
        String mail) {
}

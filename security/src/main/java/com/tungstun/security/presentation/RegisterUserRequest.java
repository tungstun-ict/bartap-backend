package com.tungstun.security.presentation;

public record RegisterUserRequest(
        String username,
        String password,
        String mail,
        String firstName,
        String lastName) {
}

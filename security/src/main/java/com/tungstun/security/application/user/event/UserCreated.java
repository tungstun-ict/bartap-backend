package com.tungstun.security.application.user.event;

public record UserCreated(
        Long id,
        String username,
        String firstName,
        String lastName,
        String mail,
        String phoneNumber) {
}

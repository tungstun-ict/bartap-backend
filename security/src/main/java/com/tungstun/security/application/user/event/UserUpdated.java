package com.tungstun.security.application.user.event;

public record UserUpdated(
        Long id,
        String username,
        String firstName,
        String lastName,
        String phoneNumber) {
}

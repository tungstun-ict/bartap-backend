package com.tungstun.security.application.user.event;

public record NameUpdated(
        Long id,
        String username,
        String firstName,
        String lastName) {
}

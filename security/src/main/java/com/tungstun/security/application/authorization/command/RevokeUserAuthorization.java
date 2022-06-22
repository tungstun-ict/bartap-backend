package com.tungstun.security.application.authorization.command;

public record RevokeUserAuthorization(
        Long ownerId,
        Long barId,
        Long userId) {
}

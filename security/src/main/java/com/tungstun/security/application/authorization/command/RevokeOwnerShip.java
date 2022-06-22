package com.tungstun.security.application.authorization.command;

public record RevokeOwnerShip(
        Long ownerId,
        Long barId) {
}

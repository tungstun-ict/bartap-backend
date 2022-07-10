package com.tungstun.security.application.authorization.command;

public record RevokeBarOwnerShip(
        Long ownerId,
        Long barId) {
}

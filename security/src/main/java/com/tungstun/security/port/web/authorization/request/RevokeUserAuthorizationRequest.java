package com.tungstun.security.port.web.authorization.request;

public record RevokeUserAuthorizationRequest(
        Long ownerId,
        Long barId,
        Long userId) {
}

package com.tungstun.security.port.web.authorization.request;

public record AuthorizeUserRequest(
        Long ownerId,
        String role,
        Long barId,
        Long userId) {
}

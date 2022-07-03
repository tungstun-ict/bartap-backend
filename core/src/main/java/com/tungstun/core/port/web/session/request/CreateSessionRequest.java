package com.tungstun.core.port.web.session.request;

public record CreateSessionRequest(
        Long barId,
        String name) {
}

package com.tungstun.core.application.session.event;

public record SessionLocked(
        Long id,
        Boolean isLocked) {

    public SessionLocked(Long id) {
        this(id, true);
    }
}

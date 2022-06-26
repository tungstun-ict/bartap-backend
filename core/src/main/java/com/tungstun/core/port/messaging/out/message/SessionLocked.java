package com.tungstun.core.port.messaging.out.message;

public record SessionLocked(
        Long id,
        Boolean isLocked) {

    public SessionLocked(Long id) {
        this(id, true);
    }
}

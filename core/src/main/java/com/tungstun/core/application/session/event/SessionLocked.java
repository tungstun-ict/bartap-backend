package com.tungstun.core.application.session.event;

import com.tungstun.common.messaging.Event;

@Event
public record SessionLocked(
        Long id,
        Boolean isLocked) {

    public SessionLocked(Long id) {
        this(id, true);
    }
}

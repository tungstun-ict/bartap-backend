package com.tungstun.person.application.person.event;

import com.tungstun.common.messaging.Event;

@Event
public record PersonUpdated(
        Long personId,
        Long barId,
        Long userId,
        String name) {
}

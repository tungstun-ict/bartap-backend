package com.tungstun.person.application.person.event;

import com.tungstun.common.messaging.Event;

@Event
public record PersonCreated(
        Long personId,
        Long barId,
        Long userId,
        String name) {
}

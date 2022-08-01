package com.tungstun.security.port.messaging.in.core.message;

import com.tungstun.common.messaging.Event;

@Event
public record BarCreated(
        Long barId,
        Long userId) {
}

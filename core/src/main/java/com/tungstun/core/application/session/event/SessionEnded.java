package com.tungstun.core.application.session.event;

import com.tungstun.common.messaging.Event;

import java.time.LocalDateTime;

@Event
public record SessionEnded(
        Long id,
        LocalDateTime endDate) {
}

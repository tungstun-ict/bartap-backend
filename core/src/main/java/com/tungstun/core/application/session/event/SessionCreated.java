package com.tungstun.core.application.session.event;

import com.tungstun.common.messaging.Event;

@Event
public record SessionCreated(Long id, Long barId) {
}

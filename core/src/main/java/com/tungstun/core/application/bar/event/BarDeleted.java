package com.tungstun.core.application.bar.event;

import com.tungstun.common.messaging.Event;

@Event
public record BarDeleted(Long id) {
}

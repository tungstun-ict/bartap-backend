package com.tungstun.product.application.product.event;

import com.tungstun.common.messaging.Event;

@Event
public record ProductDeleted(
        Long id,
        Long barId) {
}

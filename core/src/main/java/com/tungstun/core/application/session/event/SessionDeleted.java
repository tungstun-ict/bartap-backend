package com.tungstun.core.application.session.event;

import com.tungstun.common.messaging.Event;

@Event
public record SessionDeleted(Long id, Long barId) {
}

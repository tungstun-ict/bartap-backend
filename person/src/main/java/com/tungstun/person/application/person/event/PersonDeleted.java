package com.tungstun.person.application.person.event;

import com.tungstun.common.messaging.Event;

@Event
public record PersonDeleted(Long personId) {
}

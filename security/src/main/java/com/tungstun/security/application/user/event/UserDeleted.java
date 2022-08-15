package com.tungstun.security.application.user.event;

import com.tungstun.common.messaging.Event;

@Event
public record UserDeleted(Long id) {
}

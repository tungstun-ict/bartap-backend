package com.tungstun.person.port.messaging.in.user.message;

import com.tungstun.common.messaging.Event;

@Event
public record UserDeleted(Long id) {
}

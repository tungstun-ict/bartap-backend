package com.tungstun.security.application.user.event;

import com.tungstun.common.messaging.Event;

@Event
public record UserUpdated(
        Long id,
        String username,
        String firstName,
        String lastName,
        String phoneNumber) {
}

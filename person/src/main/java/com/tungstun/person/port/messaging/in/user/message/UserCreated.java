package com.tungstun.person.port.messaging.in.user.message;

import com.tungstun.common.messaging.Event;

@Event
public record UserCreated(
        Long id,
        String username,
        String firstName,
        String lastName,
        String mail,
        String phoneNumber) {
}

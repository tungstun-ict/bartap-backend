package com.tungstun.bill.port.messaging.in.person.message;

import com.tungstun.common.messaging.Event;

@Event
public record PersonCreated(
        Long id,
        Long barId,
        String username) {
}

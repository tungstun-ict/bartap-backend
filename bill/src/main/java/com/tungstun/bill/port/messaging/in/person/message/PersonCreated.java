package com.tungstun.bill.port.messaging.in.person.message;

public record PersonCreated(
        Long id,
        Long barId,
        String username) {
}

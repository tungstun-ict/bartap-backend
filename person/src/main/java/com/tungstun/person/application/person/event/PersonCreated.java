package com.tungstun.person.application.person.event;

public record PersonCreated(
        Long personId,
        Long barId,
        Long userId,
        String name) {
}

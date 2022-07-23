package com.tungstun.person.application.person.event;

public record PersonUpdated(
        Long personId,
        Long barId,
        Long userId,
        String name) {
}

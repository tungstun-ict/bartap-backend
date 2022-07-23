package com.tungstun.person.port.web.person.request;

public record CreatePersonRequest(
        String name,
        Long userId) {
}

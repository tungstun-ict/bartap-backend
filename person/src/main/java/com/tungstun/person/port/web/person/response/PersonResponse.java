package com.tungstun.person.port.web.person.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tungstun.person.domain.person.Person;

public record PersonResponse(
        Long id,
        Long barId,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Long userId,
        String name) {
    public static PersonResponse from(Person person) {
        Long userId = person.getUser() != null
                ? person.getUser().getId()
                : null;

        return new PersonResponse(
                person.getId(),
                person.getBarId(),
                userId,
                person.getName()
        );
    }
}

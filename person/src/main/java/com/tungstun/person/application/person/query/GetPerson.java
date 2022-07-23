package com.tungstun.person.application.person.query;

import javax.validation.constraints.NotNull;

public record GetPerson(
        @NotNull(message = "Person id cannot be empty")
        Long personId,
        @NotNull(message = "Bar id cannot be empty")
        Long barId) {
}

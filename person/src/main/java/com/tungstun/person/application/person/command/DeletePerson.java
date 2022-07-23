package com.tungstun.person.application.person.command;

import javax.validation.constraints.NotNull;

public record DeletePerson(
        @NotNull(message = "Person id cannot be empty")
        Long personId,
        @NotNull(message = "Bar id cannot be empty")
        Long barId) {
}

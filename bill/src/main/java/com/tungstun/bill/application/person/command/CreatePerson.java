package com.tungstun.bill.application.person.command;

import javax.validation.constraints.NotNull;

public record CreatePerson(
        @NotNull(message = "Person id cannot be empty")
        Long id,
        @NotNull(message = "Bar id cannot be empty")
        Long barId,
        @NotNull(message = "Username cannot be empty")
        String username) {
}

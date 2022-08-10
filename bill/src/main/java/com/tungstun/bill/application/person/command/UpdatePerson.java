package com.tungstun.bill.application.person.command;

import javax.validation.constraints.NotNull;

public record UpdatePerson(
        @NotNull(message = "Person id cannot be empty")
        Long id,
        @NotNull(message = "Username cannot be empty")
        String username) {
}

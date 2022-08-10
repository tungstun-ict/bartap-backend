package com.tungstun.bill.application.person.command;

import javax.validation.constraints.NotNull;

public record DeletePerson(
        @NotNull(message = "Person id cannot be empty")
        Long id) {
}

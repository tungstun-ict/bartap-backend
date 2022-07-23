package com.tungstun.person.application.person.command;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record CreatePerson(
        @NotNull(message = "Bar id cannot be empty")
        Long barId,
        @NotBlank(message = "Name cannot be empty")
        String name,
        Long userId) {
}

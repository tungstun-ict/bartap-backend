package com.tungstun.core.application.session.command;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record CreateSession(
        @NotNull(message = "Bar id cannot be empty")
        Long barId,
        @NotBlank(message = "Session name cannot be empty")
        String name) {
}

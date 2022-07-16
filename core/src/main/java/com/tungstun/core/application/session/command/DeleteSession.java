package com.tungstun.core.application.session.command;

import javax.validation.constraints.NotNull;

public record DeleteSession(
        @NotNull(message = "Session id cannot be empty")
        Long id,
        @NotNull(message = "Bar id cannot be empty")
        Long barId) {
}

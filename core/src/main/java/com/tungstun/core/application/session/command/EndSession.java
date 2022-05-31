package com.tungstun.core.application.session.command;

import javax.validation.constraints.NotNull;

public record EndSession(
        @NotNull(message = "Id cannot be empty")
        Long id) {
}

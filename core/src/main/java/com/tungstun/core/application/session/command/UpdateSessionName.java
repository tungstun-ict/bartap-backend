package com.tungstun.core.application.session.command;

import javax.validation.constraints.NotNull;

public record UpdateSessionName(
        @NotNull(message = "Id cannot be empty")
        Long id,
        @NotNull(message = "Name cannot be blank")
        String name) {
}

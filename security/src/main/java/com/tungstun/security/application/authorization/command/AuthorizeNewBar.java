package com.tungstun.security.application.authorization.command;

import javax.validation.constraints.NotNull;

public record AuthorizeNewBar(
        @NotNull(message = "Bar id cannot be null")
        Long barId,

        @NotNull(message = "User id cannot be null")
        Long userId
) {
}

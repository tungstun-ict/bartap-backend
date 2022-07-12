package com.tungstun.core.application.session.query;

import javax.validation.constraints.NotNull;

public record GetSession(
        @NotNull(message = "Session id cannot be empty")
        Long id,
        @NotNull(message = "Bar id cannot be empty")
        Long barId) {
}

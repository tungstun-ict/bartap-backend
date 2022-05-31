package com.tungstun.core.application.session.query;

import javax.validation.constraints.NotNull;

public record GetActiveSession(
        @NotNull(message = "barId cannot be empty")
        Long barId) {
}

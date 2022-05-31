package com.tungstun.core.application.session.query;

import javax.validation.constraints.NotNull;

public record ListSessionsOfBar(
        @NotNull(message = "barId cannot be empty")
        Long barId) {
}

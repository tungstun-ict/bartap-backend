package com.tungstun.core.application.session.query;

import javax.validation.constraints.NotNull;

public record GetSession(
        @NotNull(message = "id cannot be empty")
        Long id) {
}
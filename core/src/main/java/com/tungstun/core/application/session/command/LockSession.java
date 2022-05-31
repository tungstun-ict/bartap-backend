package com.tungstun.core.application.session.command;

import javax.validation.constraints.NotNull;

public record LockSession(
        @NotNull(message = "Id cannot be empty")
        Long id) {
}
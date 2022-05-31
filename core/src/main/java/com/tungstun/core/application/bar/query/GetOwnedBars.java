package com.tungstun.core.application.bar.query;

import javax.validation.constraints.NotNull;

public record GetOwnedBars(
        @NotNull(message = "User id cannot be empty")
        Long userId) {
}

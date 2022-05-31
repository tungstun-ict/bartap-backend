package com.tungstun.core.application.bar.query;

import javax.validation.constraints.NotNull;

public record GetBar(
        @NotNull(message = "id cannot be empty")
        Long id) {
}

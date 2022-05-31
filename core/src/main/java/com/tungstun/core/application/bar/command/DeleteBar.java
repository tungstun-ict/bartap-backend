package com.tungstun.core.application.bar.command;

import javax.validation.constraints.NotNull;

public record DeleteBar(
        @NotNull(message = "Id cannot be null")
        Long id) {
}

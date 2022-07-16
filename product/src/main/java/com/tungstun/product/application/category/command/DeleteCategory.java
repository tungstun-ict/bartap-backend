package com.tungstun.product.application.category.command;

import javax.validation.constraints.NotNull;

public record DeleteCategory(
        @NotNull(message = "Category id cannot be empty")
        Long id,
        @NotNull(message = "Bar id cannot be empty")
        Long barId) {
}

package com.tungstun.product.application.category.command;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record CreateCategory(
        @NotBlank(message = "Category name cannot be blank")
        String name,
        @NotNull(message = "Bar id cannot be empty")
        Long barId) {
}

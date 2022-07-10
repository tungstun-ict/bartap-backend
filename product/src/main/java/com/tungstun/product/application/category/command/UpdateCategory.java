package com.tungstun.product.application.category.command;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record UpdateCategory(
        @NotNull(message = "Category id cannot be empty")
        Long id,
        @NotBlank(message = "Category name cannot be blank")
        String name) {
}

package com.tungstun.product.application.category.query;

import javax.validation.constraints.NotNull;

public record GetCategory(
        @NotNull(message = "Category id cannot be empty")
        Long id) {
}

package com.tungstun.product.application.product.query;

import javax.validation.constraints.NotNull;

public record ListProductsOfCategory(
        @NotNull(message = "Category id cannot be empty")
        Long categoryId,
        @NotNull(message = "Bar id cannot be empty")
        Long barId) {
}

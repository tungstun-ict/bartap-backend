package com.tungstun.product.application.category.query;

import javax.validation.constraints.NotNull;

public record ListCategoriesOfBar(
        @NotNull(message = "Bar id cannot be empty")
        Long barId) {
}

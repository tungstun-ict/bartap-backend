package com.tungstun.product.application.product.query;

import javax.validation.constraints.NotNull;

public record GetProduct(
        @NotNull(message = "Product id cannot be empty")
        Long id,
        @NotNull(message = "Bar id cannot be empty")
        Long barId) {
}

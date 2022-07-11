package com.tungstun.product.application.product.command;

import javax.validation.constraints.NotNull;

public record DeleteProduct(
        @NotNull(message = "Product id cannot be empty")
        Long id,
        @NotNull(message = "Bar id cannot be empty")
        Long barId) {
}

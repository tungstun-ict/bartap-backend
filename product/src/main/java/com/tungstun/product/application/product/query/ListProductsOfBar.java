package com.tungstun.product.application.product.query;

import javax.validation.constraints.NotNull;

public record ListProductsOfBar(
        @NotNull(message = "Bar id cannot be empty")
        Long barId) {
}

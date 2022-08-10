package com.tungstun.bill.application.product.query;

import javax.validation.constraints.NotNull;

public record GetProduct(
        @NotNull(message = "Product id cannot be empty")
        Long id) {
}

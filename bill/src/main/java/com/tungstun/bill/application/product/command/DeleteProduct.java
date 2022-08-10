package com.tungstun.bill.application.product.command;

import javax.validation.constraints.NotNull;

public record DeleteProduct(
        @NotNull(message = "Product id cannot be empty")
        Long id) {
}

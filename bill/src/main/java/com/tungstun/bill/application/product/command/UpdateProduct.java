package com.tungstun.bill.application.product.command;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public record UpdateProduct(
        @NotNull(message = "Product id cannot be empty")
        Long id,
        @NotNull(message = "Name cannot be empty")
        String name,
        @NotNull(message = "Brand cannot be empty")
        String brand,
        @PositiveOrZero(message = "Price cannot be a negative value")
        Double price,
        @NotNull(message = "Currency cannot be empty")
        String currencySymbol,
        @NotNull(message = "Currency cannot be empty")
        String currencyCode) {
}

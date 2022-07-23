package com.tungstun.bill.application.bill.command;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public record AddOrder(
        @NotNull(message = "Bill id cannot be empty")
        Long id,
        @NotNull(message = "Bar id cannot be empty")
        Long barId,
        @NotNull(message = "Bartender person id cannot be empty")
        Long bartenderId,
        @NotNull(message = "Product id cannot be empty")
        Long productId,
        @Positive(message = "Amount must be more than 0")
        Integer amount) {
}

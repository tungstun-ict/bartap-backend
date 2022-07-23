package com.tungstun.bill.application.bill.command;

import javax.validation.constraints.NotNull;

public record RemoveOrder(
        @NotNull(message = "Bill id cannot be empty")
        Long id,
        @NotNull(message = "Bar id cannot be empty")
        Long barId,
        @NotNull(message = "Order id cannot be empty")
        Long orderId,
        @NotNull(message = "Bartender person id cannot be empty")
        Long bartenderId) {
}

package com.tungstun.bill.application.bill.command;

import javax.validation.constraints.NotNull;

public record DeleteBill(
        @NotNull(message = "Id cannot be empty")
        Long id,
        @NotNull(message = "Bar id cannot be empty")
        Long barId) {
}

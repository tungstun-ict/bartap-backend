package com.tungstun.bill.application.bill.command;

import javax.validation.constraints.NotNull;

public record CreateBill(
        @NotNull(message = "Bar id cannot be empty")
        Long barId,
        @NotNull(message = "Session id cannot be empty")
        Long sessionId,
        @NotNull(message = "Customer id cannot be empty")
        Long customerId) {
}

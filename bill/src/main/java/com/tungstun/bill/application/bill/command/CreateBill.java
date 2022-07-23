package com.tungstun.bill.application.bill.command;

import javax.validation.constraints.NotNull;

public record CreateBill(
        @NotNull(message = "Session id cannot be empty")
        Long sessionId,
        @NotNull(message = "Customer person id cannot be empty")
        Long customerId,
        @NotNull(message = "Bar id cannot be empty")
        Long barId) {
}

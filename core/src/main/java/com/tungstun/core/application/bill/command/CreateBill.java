package com.tungstun.core.application.bill.command;

import javax.validation.constraints.NotNull;

public record CreateBill(
        @NotNull(message = "Session id cannot be empty")
        Long sessionId,
        @NotNull(message = "Customer id cannot be empty")
        Long customerId) {
}

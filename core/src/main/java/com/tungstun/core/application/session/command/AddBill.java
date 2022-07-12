package com.tungstun.core.application.session.command;

import javax.validation.constraints.NotNull;

public record AddBill(
        @NotNull(message = "Bar id cannot be empty")
        Long barId,
        @NotNull(message = "Session id cannot be empty")
        Long sessionId,
        @NotNull(message = "Bill Id cannot be empty")
        Long billId) {
}

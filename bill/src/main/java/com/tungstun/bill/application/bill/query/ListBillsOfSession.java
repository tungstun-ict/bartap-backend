package com.tungstun.bill.application.bill.query;

import javax.validation.constraints.NotNull;

public record ListBillsOfSession(
        @NotNull(message = "Session id cannot be empty")
        Long sessionId,
        @NotNull(message = "Bar id cannot be empty")
        Long barId) {
}

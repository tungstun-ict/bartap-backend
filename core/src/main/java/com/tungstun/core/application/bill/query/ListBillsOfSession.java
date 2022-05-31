package com.tungstun.core.application.bill.query;

import javax.validation.constraints.NotNull;

public record ListBillsOfSession(
        @NotNull(message = "Session id cannot be empty")
        Long sessionId) {
}

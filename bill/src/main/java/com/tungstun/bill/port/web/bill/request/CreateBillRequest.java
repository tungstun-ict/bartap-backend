package com.tungstun.bill.port.web.bill.request;

import javax.validation.constraints.NotNull;

public record CreateBillRequest(
        @NotNull(message = "Session id cannot be empty")
        Long sessionId,
        @NotNull(message = "Customer id cannot be empty")
        Long customerId) {
}

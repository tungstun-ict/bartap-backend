package com.tungstun.bill.application.bill.query;

import javax.validation.constraints.NotNull;

public record ListOrderHistory(
        @NotNull(message = "Bill id cannot be empty")
        Long billId,
        @NotNull(message = "Bar id cannot be empty")
        Long barId) {
}

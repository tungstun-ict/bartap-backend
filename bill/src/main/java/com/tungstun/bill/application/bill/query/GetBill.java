package com.tungstun.bill.application.bill.query;

import javax.validation.constraints.NotNull;

public record GetBill(
        @NotNull(message = "Bill id cannot be empty")
        Long id,
        @NotNull(message = "Bar id cannot be empty")
        Long barId) {
}

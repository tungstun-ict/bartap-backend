package com.tungstun.bill.application.bill.query;

import javax.validation.constraints.NotNull;

public record GetBill(
        @NotNull(message = "id cannot be empty")
        Long id) {
}

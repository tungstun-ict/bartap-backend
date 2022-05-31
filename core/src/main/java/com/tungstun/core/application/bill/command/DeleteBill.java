package com.tungstun.core.application.bill.command;

import javax.validation.constraints.NotNull;

public record DeleteBill(
        @NotNull(message = "Id cannot be empty")
        Long id) {
}

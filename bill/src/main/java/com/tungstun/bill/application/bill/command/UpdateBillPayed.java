package com.tungstun.bill.application.bill.command;

import javax.validation.constraints.NotNull;

public record UpdateBillPayed(
        @NotNull(message = "Id cannot be empty")
        Long id,
        @NotNull(message = "Boolean payed cannot be null")
        Boolean payed) {
}

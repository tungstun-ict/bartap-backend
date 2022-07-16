package com.tungstun.bill.application.bill.query;

import javax.validation.constraints.NotNull;

public record ListBillsOfPerson(
        @NotNull(message = "Person id cannot be empty")
        Long personId,
        @NotNull(message = "Bar id cannot be empty")
        Long barId) {
}

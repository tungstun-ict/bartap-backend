package com.tungstun.core.application.bill.query;

import javax.validation.constraints.NotNull;

public record ListBillsOfPerson(
        @NotNull(message = "Person id cannot be empty")
        Long personId) {
}

package com.tungstun.core.application.session.command;

import javax.validation.constraints.NotNull;

public record RemoveBill(
        @NotNull(message = "Id cannot be empty")
        Long id,

        @NotNull(message = "Bill Id cannot be empty")
        Long billId) {
}

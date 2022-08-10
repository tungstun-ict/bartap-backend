package com.tungstun.bill.application.person.query;

import javax.validation.constraints.NotNull;

public record GetPerson(
        @NotNull(message = "Person id cannot be empty")
        Long id) {
}

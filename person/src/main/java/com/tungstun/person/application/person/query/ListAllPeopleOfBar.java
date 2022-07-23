package com.tungstun.person.application.person.query;

import javax.validation.constraints.NotNull;

public record ListAllPeopleOfBar(
        @NotNull(message = "Bar id cannot be empty")
        Long barId) {
}

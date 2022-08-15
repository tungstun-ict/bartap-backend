package com.tungstun.person.application.user.query;

import javax.validation.constraints.NotNull;

public record GetUser(
        @NotNull(message = "User id cannot be empty")
        Long id) {
}

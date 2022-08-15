package com.tungstun.person.application.user.command;

import javax.validation.constraints.NotNull;

public record DeleteUser(
        @NotNull(message = "User id cannot be empty")
        Long id) {
}

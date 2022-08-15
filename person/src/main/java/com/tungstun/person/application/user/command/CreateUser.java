package com.tungstun.person.application.user.command;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record CreateUser(
        @NotNull(message = "User id cannot be empty")
        Long id,
        @NotEmpty(message = "Username cannot be empty")
        String username,
        String phoneNumber,
        String fullName) {
}

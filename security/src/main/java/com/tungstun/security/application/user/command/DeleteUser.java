package com.tungstun.security.application.user.command;

import javax.validation.constraints.NotBlank;

public record DeleteUser(
        @NotBlank(message = "Username cannot be empty")
        String username) {
}

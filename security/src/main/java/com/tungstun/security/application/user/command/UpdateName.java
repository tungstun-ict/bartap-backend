package com.tungstun.security.application.user.command;

import javax.validation.constraints.NotBlank;

public record UpdateName(
        @NotBlank(message = "Username cannot be empty")
        String username,
        @NotBlank(message = "First name cannot be empty")
        String firstName,
        @NotBlank(message = "Last name cannot be empty")
        String lastName) {
}

package com.tungstun.security.application.user.command;

import javax.validation.constraints.NotBlank;

public record UpdateUser(
        @NotBlank(message = "Username cannot be empty")
        String username,
        String firstName,
        String lastName,
        String phoneNumber) {
}

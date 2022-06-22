package com.tungstun.security.application.authorization.command;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record AuthorizeUser(
        @NotNull(message = "Bar owner's user id cannot be null")
        Long ownerId,

        @NotBlank(message = "Role value cannot be blank")
        String role,

        @NotNull(message = "Bar id cannot be null")
        Long barId,

        @NotNull(message = "User id cannot be null")
        Long userId
) {
}

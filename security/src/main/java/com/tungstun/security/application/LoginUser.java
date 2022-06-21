package com.tungstun.security.application;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record LoginUser(
        @NotBlank(message = "Username cannot be blank")
        String username,

        @Size(min = 5, max = 50, message = "Password must be between {min} and {max} characters long")
        @NotBlank(message = "Username cannot be blank")
        String password) {
}

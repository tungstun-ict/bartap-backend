package com.tungstun.security.application;

import javax.validation.constraints.NotBlank;

public record VerifyUser(
        @NotBlank(message = "Access token cannot be blank")
        String accessToken,

        @NotBlank(message = "Token type token cannot be blank")
        String tokenType) {
}

package com.tungstun.security.application.user.command;

import javax.validation.constraints.NotBlank;

public record RefreshAccessToken(
        @NotBlank(message = "Access token cannot be blank")
        String accessToken,

        @NotBlank(message = "Refresh token cannot be blank")
        String refreshToken) {
}

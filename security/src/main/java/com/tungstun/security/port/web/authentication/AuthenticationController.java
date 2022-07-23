package com.tungstun.security.port.web.authentication;

import com.tungstun.security.application.user.UserCommandHandler;
import com.tungstun.security.application.user.command.LoginUser;
import com.tungstun.security.application.user.command.RefreshAccessToken;
import com.tungstun.security.application.user.command.RegisterUser;
import com.tungstun.security.application.user.command.VerifyUser;
import com.tungstun.security.port.web.authentication.request.LoginUserRequest;
import com.tungstun.security.port.web.authentication.request.RegisterUserRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import java.util.Map;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    private final UserCommandHandler userCommandHandler;

    public AuthenticationController(UserCommandHandler userCommandHandler) {
        this.userCommandHandler = userCommandHandler;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Register a new user account",
            description = "A new user account is created with the information provided in the request body",
            tags = "User"
    )
    public void register(@RequestBody RegisterUserRequest request) {
        userCommandHandler.handle(new RegisterUser(
                request.username(),
                request.password(),
                request.mail(),
                request.phoneNumber(),
                request.firstName(),
                request.lastName()
        ));
    }

    @PostMapping("/login")
    @Operation(
            summary = "Login user",
            description = "Logs in a user based on the information provided in the request body and return an access and refresh token",
            tags = "Authentication"
    )
    public ResponseEntity<Void> login(@RequestBody LoginUserRequest loginRequest) throws LoginException {
        Map<String, String> authorization = this.userCommandHandler.handle(
                new LoginUser(loginRequest.username(), loginRequest.password()));
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setAll(authorization);
        return ResponseEntity.ok().headers(responseHeaders).build();
    }

    @PostMapping("/refresh")
    @Operation(
            summary = "Refresh access token",
            description = "Refreshes a user's access token using the refresh token and expired access token",
            tags = "Authentication"
    )
    public ResponseEntity<Void> refresh(@RequestHeader("access_token") String accessToken,
                                        @RequestHeader("refresh_token") String refreshToken) {
        Map<String, String> authorization = this.userCommandHandler.handle(new RefreshAccessToken(accessToken, refreshToken));
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setAll(authorization);
        return ResponseEntity.ok().headers(responseHeaders).build();
    }

    @PostMapping("/verify")
    @Operation(
            summary = "Verify access token",
            description = "Verifies the validity of the provided access token",
            tags = "Authentication"
    )
    public void verify(@RequestHeader("access_token") String accessToken,
                       @RequestHeader("token_type") String tokenType) {
        this.userCommandHandler.handle(new VerifyUser(accessToken, tokenType));
    }
}

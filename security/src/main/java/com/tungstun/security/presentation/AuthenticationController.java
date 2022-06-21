package com.tungstun.security.presentation;

import com.tungstun.security.application.*;
import com.tungstun.security.presentation.request.LoginUserRequest;
import com.tungstun.security.presentation.request.RegisterUserRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody RegisterUserRequest request) {
        userService.registerUser(new RegisterUser(
                request.username(),
                request.password(),
                request.mail(),
                request.firstName(),
                request.lastName()
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginUserRequest loginRequest) throws LoginException {
        Map<String, String> authorization = this.userService.loginUser(
                new LoginUser(loginRequest.username(), loginRequest.password()));
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setAll(authorization);
        return ResponseEntity.ok().headers(responseHeaders).build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<Void> refresh(@RequestHeader("access_token") String accessToken,
                                        @RequestHeader("refresh_token") String refreshToken) {
        Map<String, String> authorization = this.userService.refreshUser(new RefreshAccessToken(accessToken, refreshToken));
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setAll(authorization);
        return ResponseEntity.ok().headers(responseHeaders).build();
    }

    @PostMapping("/verify")
    public void verify(@RequestHeader("access_token") String accessToken,
                       @RequestHeader("token_type") String tokenType) {
        this.userService.verifyUser(new VerifyUser(accessToken, tokenType));
    }
}

package com.tungstun.security.presentation;

import com.tungstun.security.application.RegisterUser;
import com.tungstun.security.application.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void register(RegisterUserRequest request) {
        userService.registerUser(new RegisterUser(
                request.username(),
                request.password(),
                request.firstName(),
                request.lastName(),
                request.mail()
        ));
    }
}

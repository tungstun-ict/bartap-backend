package com.tungstun.security.presentation;

import com.tungstun.security.application.RegisterUser;
import com.tungstun.security.application.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
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
}

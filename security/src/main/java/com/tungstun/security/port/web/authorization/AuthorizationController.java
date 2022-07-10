package com.tungstun.security.port.web.authorization;

import com.tungstun.security.application.authorization.AuthorizationCommandHandler;
import com.tungstun.security.application.authorization.command.AuthorizeUser;
import com.tungstun.security.application.authorization.command.RevokeUserAuthorization;
import com.tungstun.security.port.web.authorization.request.AuthorizeUserRequest;
import com.tungstun.security.port.web.authorization.request.RevokeUserAuthorizationRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authorization")
public class AuthorizationController {
    private final AuthorizationCommandHandler authorizationCommandHandler;

    public AuthorizationController(AuthorizationCommandHandler authorizationCommandHandler) {
        this.authorizationCommandHandler = authorizationCommandHandler;
    }

    @PostMapping("/authorize-user")
    @ResponseStatus(HttpStatus.OK)
    public void authorizeUser(@RequestBody AuthorizeUserRequest request) {
        authorizationCommandHandler.handle(new AuthorizeUser(
                request.ownerId(),
                request.role(),
                request.barId(),
                request.userId()));
    }

    @PostMapping("/unauthorize-user")
    @ResponseStatus(HttpStatus.OK)
    public void authorizeUser(@RequestBody RevokeUserAuthorizationRequest request) {
        authorizationCommandHandler.handle(new RevokeUserAuthorization(
                request.ownerId(),
                request.barId(),
                request.userId()));
    }
}

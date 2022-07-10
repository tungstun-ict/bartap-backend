package com.tungstun.security.port.web.authorization;

import com.tungstun.security.application.authorization.AuthorizationCommandHandler;
import com.tungstun.security.application.authorization.command.AuthorizeUser;
import com.tungstun.security.application.authorization.command.RevokeUserAuthorization;
import com.tungstun.security.port.web.authorization.request.AuthorizeUserRequest;
import com.tungstun.security.port.web.authorization.request.RevokeUserAuthorizationRequest;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(
            summary = "Authorize account",
            description = "An account is authorized with the given role for the bar with provided bar id",
            tags = "Authorization"
    )
    public void authorizeUser(@RequestBody AuthorizeUserRequest request) {
        authorizationCommandHandler.handle(new AuthorizeUser(
                request.ownerId(),
                request.role(),
                request.barId(),
                request.userId()));
    }

    @PostMapping("/unauthorize-user")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Revoke authorization of account",
            description = "An account's authorization is revoked for the bar with provided bar id",
            tags = "Authorization"
    )
    public void authorizeUser(@RequestBody RevokeUserAuthorizationRequest request) {
        authorizationCommandHandler.handle(new RevokeUserAuthorization(
                request.ownerId(),
                request.barId(),
                request.userId()));
    }
}

package com.tungstun.security.application.authorization;

import com.tungstun.security.application.authorization.command.AuthorizeNewBar;
import com.tungstun.security.application.authorization.command.AuthorizeUser;
import com.tungstun.security.application.authorization.command.RevokeOwnerShip;
import com.tungstun.security.application.authorization.command.RevokeUserAuthorization;
import com.tungstun.security.domain.user.Role;
import com.tungstun.security.domain.user.User;
import com.tungstun.security.domain.user.UserRepository;
import com.tungstun.sharedlibrary.exception.UserNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

@Controller
@Validated
public class AuthorizationService {
    private final UserRepository userRepository;

    public AuthorizationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User loadUserById(Long id) throws UsernameNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id '%s' was not found", id)));
    }

    public boolean handle(AuthorizeUser command) {
        User owner = loadUserById(command.ownerId());
        User user = loadUserById(command.userId());
        return owner.authorizeUser(user, command.barId(), Role.getRole(command.role()));
    }

    public boolean handle(AuthorizeNewBar command) {
        User user = loadUserById(command.userId());
        return user.newBarAuthorization(command.barId());
    }

    public boolean handle(RevokeUserAuthorization command) {
        User owner = loadUserById(command.ownerId());
        User user = loadUserById(command.userId());
        return owner.revokeUserAuthorization(user, command.barId());
    }

    public boolean handle(RevokeOwnerShip command) {
        User owner = loadUserById(command.ownerId());
        return owner.revokeOwnership(command.barId());
    }
}

package com.tungstun.security.application.authorization;

import com.tungstun.common.exception.UserNotFoundException;
import com.tungstun.security.application.authorization.command.AuthorizeNewBarOwnership;
import com.tungstun.security.application.authorization.command.AuthorizeUser;
import com.tungstun.security.application.authorization.command.RevokeBarOwnerShip;
import com.tungstun.security.application.authorization.command.RevokeUserAuthorization;
import com.tungstun.security.domain.user.Role;
import com.tungstun.security.domain.user.User;
import com.tungstun.security.domain.user.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Service
@Validated
@Transactional
public class AuthorizationCommandHandler {
    private final UserRepository userRepository;

    public AuthorizationCommandHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User loadUserById(Long id) throws UsernameNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id '%s' was not found", id)));
    }

    public boolean handle(@Valid AuthorizeUser command) {
        User owner = loadUserById(command.ownerId());
        User user = loadUserById(command.userId());
        return owner.authorizeUser(user, command.barId(), Role.getRole(command.role()));
    }

    public boolean handle(@Valid RevokeUserAuthorization command) {
        User owner = loadUserById(command.ownerId());
        User user = loadUserById(command.userId());
        return owner.revokeUserAuthorization(user, command.barId());
    }

    public boolean handle(@Valid AuthorizeNewBarOwnership command) {
        User user = loadUserById(command.userId());
        return user.newBarAuthorization(command.barId());
    }

    /**
     * DON'T USE
     * Currently only removes a user's ownership of bar. After removing user cannot undo or get access back to the bar.
     * Implement soft delete or give access to other user.
     */
    public boolean handle(@Valid RevokeBarOwnerShip command) {
        User owner = loadUserById(command.ownerId());
        return owner.revokeOwnership(command.barId());
    }
}

package com.tungstun.person.application.user;

import com.tungstun.person.application.user.query.GetUser;
import com.tungstun.person.domain.user.User;
import com.tungstun.person.domain.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;

@Service
@Validated
public class UserQueryHandler {
    private final UserRepository repository;

    public UserQueryHandler(UserRepository repository) {
        this.repository = repository;
    }

    public User handle(GetUser command) {
        return repository.findById(command.id())
                .orElseThrow(() -> new EntityNotFoundException(String.format("No user found with id %s", command.id())));
    }
}

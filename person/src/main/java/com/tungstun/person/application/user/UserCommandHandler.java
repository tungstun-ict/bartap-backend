package com.tungstun.person.application.user;

import com.tungstun.person.application.user.command.CreateUser;
import com.tungstun.person.application.user.command.DeleteUser;
import com.tungstun.person.application.user.command.UpdateUser;
import com.tungstun.person.domain.user.User;
import com.tungstun.person.domain.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;

@Service
@Validated
@Transactional
public class UserCommandHandler {
    private final UserRepository repository;

    public UserCommandHandler(UserRepository repository) {
        this.repository = repository;
    }

    public Long handle(@Valid CreateUser command) {
        return repository.save(new User(
                command.id(),
                command.username(),
                command.phoneNumber(),
                command.fullName()
        )).getId();
    }

    public Long handle(@Valid UpdateUser command) {
        User user = repository.findById(command.id())
                .orElseThrow(() -> new EntityNotFoundException(String.format("No user found with id %s", command.id())));

        user.setPhoneNumber(command.phoneNumber());
        user.setUsername(command.username());
        user.setFullName(command.fullName());

        return repository.update(user).getId();
    }

    public void handle(@Valid DeleteUser command) {
        repository.delete(command.id());
    }
}

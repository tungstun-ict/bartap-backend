package com.tungstun.core.application.session;

import com.tungstun.core.application.session.command.*;
import com.tungstun.core.domain.session.Session;
import com.tungstun.core.domain.session.SessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@Service
@Validated
public class SessionCommandHandler {
    private final SessionRepository repository;

    public SessionCommandHandler(SessionRepository repository) {
        this.repository = repository;
    }

    public Long handle(@Valid CreateSession command) {
        return repository.save(Session.with(command.barId(), command.name())).getId();
    }

    public Long handle(@Valid UpdateSessionName command) {
        Session session = getSession(command.id());
        session.setName(command.name());
        return repository.update(session).getId();
    }

    public void handle(@Valid DeleteSession command) {
        repository.delete(command.id());
    }

    public void handle(@Valid EndSession command) {
        Session session = getSession(command.id());
        session.endSession();
        repository.update(session);
    }

    public void handle(@Valid LockSession command) {
        Session session = getSession(command.id());
        session.lock();
        repository.update(session);
    }

    private Session getSession(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No session found with id %s", id)));
    }
}

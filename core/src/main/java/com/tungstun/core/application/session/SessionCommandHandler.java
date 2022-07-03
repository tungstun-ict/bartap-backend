package com.tungstun.core.application.session;

import com.tungstun.core.application.session.command.*;
import com.tungstun.core.domain.session.Session;
import com.tungstun.core.domain.session.SessionRepository;
import com.tungstun.core.port.messaging.out.KafkaCoreMessageProducer;
import com.tungstun.core.port.messaging.out.message.SessionCreated;
import com.tungstun.core.port.messaging.out.message.SessionDeleted;
import com.tungstun.core.port.messaging.out.message.SessionEnded;
import com.tungstun.core.port.messaging.out.message.SessionLocked;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@Service
@Validated
public class SessionCommandHandler {
    private final SessionRepository repository;
    private final KafkaCoreMessageProducer producer;

    public SessionCommandHandler(SessionRepository repository, KafkaCoreMessageProducer producer) {
        this.repository = repository;
        this.producer = producer;
    }

    public Long handle(@Valid CreateSession command) {
        Long id = repository.save(Session.with(command.barId(), command.name())).getId();

        producer.publish(id, new SessionCreated(id));

        return id;
    }

    public Long handle(@Valid UpdateSessionName command) {
        Session session = loadSession(command.id());
        session.setName(command.name());
        return repository.update(session).getId();
    }

    public void handle(@Valid DeleteSession command) {
        repository.delete(command.id());

        producer.publish(command.id(), new SessionDeleted(command.id()));
    }

    public void handle(@Valid EndSession command) {
        Session session = loadSession(command.id());
        session.endSession();
        session = repository.update(session);

        producer.publish(command.id(), new SessionEnded(command.id(), session.getEndDate()));
    }

    public void handle(@Valid LockSession command) {
        Session session = loadSession(command.id());
        session.lock();
        repository.update(session);

        producer.publish(command.id(), new SessionLocked(command.id()));
    }

    private Session loadSession(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No session found with id %s", id)));
    }
}

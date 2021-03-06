package com.tungstun.core.application.session;

import com.tungstun.common.messaging.KafkaMessageProducer;
import com.tungstun.core.application.session.command.*;
import com.tungstun.core.application.session.event.SessionCreated;
import com.tungstun.core.application.session.event.SessionDeleted;
import com.tungstun.core.application.session.event.SessionEnded;
import com.tungstun.core.application.session.event.SessionLocked;
import com.tungstun.core.domain.session.Session;
import com.tungstun.core.domain.session.SessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;

@Service
@Validated
@Transactional
public class SessionCommandHandler {
    private final SessionRepository repository;
    private final KafkaMessageProducer producer;

    public SessionCommandHandler(SessionRepository repository, KafkaMessageProducer producer) {
        this.repository = repository;
        this.producer = producer;
    }

    private Session loadSession(Long id, Long barId) {
        return repository.findByIdAndBarId(id, barId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No session found with id %s", id)));
    }

    public Long handle(@Valid CreateSession command) {
        Long id = repository.save(Session.with(command.barId(), command.name())).getId();

        producer.publish(id, new SessionCreated(id, command.barId()));

        return id;
    }

    public Long handle(@Valid UpdateSessionName command) {
        Session session = loadSession(command.id(), command.barId());
        session.setName(command.name());
        return repository.update(session).getId();
    }

    public void handle(@Valid DeleteSession command) {
        Session session = loadSession(command.id(), command.barId());
        repository.delete(session);

        producer.publish(command.id(), new SessionDeleted(command.id(), command.barId()));
    }

    public void handle(@Valid EndSession command) {
        Session session = loadSession(command.id(), command.barId());
        session.endSession();
        session = repository.update(session);

        producer.publish(command.id(), new SessionEnded(command.id(), session.getEndDate()));
    }

    public void handle(@Valid LockSession command) {
        Session session = loadSession(command.id(), command.barId());
        session.lock();
        repository.update(session);

        producer.publish(command.id(), new SessionLocked(command.id()));
    }

    public void handle(@Valid AddBill command) {
        Session session = loadSession(command.sessionId(), command.barId());
        session.addBill(command.billId());
        repository.update(session);
    }

    public void handle(@Valid RemoveBill command) {
        Session session = loadSession(command.sessionId(), command.barId());
        session.removeBill(command.billId());
        repository.update(session);
    }
}

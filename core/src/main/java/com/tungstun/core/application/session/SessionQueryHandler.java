package com.tungstun.core.application.session;

import com.tungstun.core.application.session.query.GetActiveSession;
import com.tungstun.core.application.session.query.GetSession;
import com.tungstun.core.application.session.query.ListSessionsOfBar;
import com.tungstun.core.domain.session.Session;
import com.tungstun.core.domain.session.SessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Service
@Validated
public class SessionQueryHandler {
    private final SessionRepository repository;

    public SessionQueryHandler(SessionRepository repository) {
        this.repository = repository;
    }

    public Session handle(@Valid GetSession query) {
        return repository.findById(query.id())
                .filter(session -> Objects.equals(session.getBarId(), query.barId()))
                .orElseThrow(() -> new EntityNotFoundException(String.format("No session found with id %s", query.id())));
    }

    public List<Session> handle(@Valid ListSessionsOfBar query) {
        return repository.findAllByBarId(query.barId());
    }

    public Session handle(@Valid GetActiveSession query) {
        return handle(new ListSessionsOfBar(query.barId()))
                .stream()
                .filter(session -> !session.endSession())
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("No active session found"));
    }
}

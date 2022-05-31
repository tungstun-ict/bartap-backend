package com.tungstun.core.application.session;

import com.tungstun.core.application.session.query.GetActiveSession;
import com.tungstun.core.application.session.query.GetSession;
import com.tungstun.core.application.session.query.ListSessionsOfBar;
import com.tungstun.core.domain.session.Session;
import com.tungstun.core.domain.session.SessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Validated
public class SessionQueryHandler {
    private final SessionRepository repository;

    public SessionQueryHandler(SessionRepository repository) {
        this.repository = repository;
    }

    public Session handle(GetSession query) {
        return repository.findById(query.id())
                .orElseThrow(() -> new EntityNotFoundException(String.format("No session found with id %s", query.id())));
    }

    public List<Session> handle(ListSessionsOfBar query) {
        return repository.findAllByBarId(query.barId());
    }

    public Session handle(GetActiveSession query) {
        return handle(new ListSessionsOfBar(query.barId()))
                .stream()
                .filter(session -> !session.endSession())
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("No active session found"));
    }
}

package com.tungstun.core.port.persistence.session;

import com.tungstun.core.domain.session.Session;
import com.tungstun.core.domain.session.SessionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DefaultSessionRepository implements SessionRepository {
    private final SpringSessionRepository repository;

    public DefaultSessionRepository(SpringSessionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Session save(Session session) {
        return repository.save(session);
    }

    @Override
    public Session update(Session session) {
        return repository.save(session);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Session> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Session> findAllByBarId(Long id) {
        return repository.findAllByBarId(id);
    }
}

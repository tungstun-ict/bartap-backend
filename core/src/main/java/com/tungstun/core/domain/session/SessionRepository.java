package com.tungstun.core.domain.session;

import com.tungstun.common.persistence.CrudRepositoryFragment;

import java.util.List;
import java.util.Optional;

public interface SessionRepository extends CrudRepositoryFragment<Session, Long> {
    Optional<Session> findByIdAndBarId(Long id, Long barId);

    List<Session> findAllByBarId(Long id);

    void delete(Session session);
}

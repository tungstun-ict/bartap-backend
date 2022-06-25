package com.tungstun.core.domain.session;

import com.tungstun.common.persistence.CrudRepositoryFragment;

import java.util.List;

public interface SessionRepository  extends CrudRepositoryFragment<Session, Long> {
    List<Session> findAllByBarId(Long id);
}

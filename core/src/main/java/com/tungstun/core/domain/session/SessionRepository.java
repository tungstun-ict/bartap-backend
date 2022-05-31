package com.tungstun.core.domain.session;

import com.tungstun.sharedlibrary.persistence.CrudRepositoryFragment;

import java.util.List;

public interface SessionRepository  extends CrudRepositoryFragment<Session, Long> {
    List<Session> findAllByBarId(Long id);
}

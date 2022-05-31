package com.tungstun.core.port.persistence.session;

import com.tungstun.core.domain.session.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringSessionRepository extends JpaRepository<Session, Long> {
    List<Session> findAllByBarId(Long id);
}

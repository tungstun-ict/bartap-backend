package com.tungstun.core.port.web.session.response;

import com.tungstun.core.domain.session.Session;

import java.time.LocalDateTime;

public record SessionResponse(
        Long id,
        String name,
        LocalDateTime creationDate,
        LocalDateTime endDate,
        Boolean isLocked) {

    public static SessionResponse of(Session session) {
        return new SessionResponse(
                session.getId(),
                session.getName(),
                session.getCreationDate(),
                session.getEndDate(),
                session.isLocked());
    }
}

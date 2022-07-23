package com.tungstun.core.application.session.event;

import java.time.LocalDateTime;

public record SessionEnded(
        Long id,
        LocalDateTime endDate) {
}

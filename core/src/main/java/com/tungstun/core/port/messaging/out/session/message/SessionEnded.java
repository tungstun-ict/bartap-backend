package com.tungstun.core.port.messaging.out.session.message;

import java.time.LocalDateTime;

public record SessionEnded(
        Long id,
        LocalDateTime endDate) {
}

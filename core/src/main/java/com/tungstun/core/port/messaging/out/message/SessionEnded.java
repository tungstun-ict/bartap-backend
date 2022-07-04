package com.tungstun.core.port.messaging.out.message;

import java.time.LocalDateTime;

public record SessionEnded(
        Long id,
        LocalDateTime endDate) {
}

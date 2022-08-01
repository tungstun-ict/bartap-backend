package com.tungstun.core.port.messaging.in.bill.message;

import com.tungstun.common.messaging.Event;

@Event
public record BillCreated(
        Long id,
        Long barId,
        Long sessionId,
        Long customerId) {
}

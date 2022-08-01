package com.tungstun.bill.application.bill.event;

import com.tungstun.common.messaging.Event;

@Event
public record BillDeleted(
        Long id,
        Long barId,
        Long sessionId,
        Long customerId) {
}

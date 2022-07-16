package com.tungstun.core.port.messaging.in.bill.message;

public record BillDeleted(
        Long id,
        Long barId,
        Long sessionId,
        Long customerId) {
}

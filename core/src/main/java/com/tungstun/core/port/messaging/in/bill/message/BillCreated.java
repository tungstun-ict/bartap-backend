package com.tungstun.core.port.messaging.in.bill.message;

public record BillCreated(
        Long id,
        Long barId,
        Long sessionId,
        Long customerId) {
}

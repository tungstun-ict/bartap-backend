package com.tungstun.bill.port.messaging.out;

public record BillDeleted(
        Long id,
        Long barId,
        Long sessionId,
        Long customerId) {
}

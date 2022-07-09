package com.tungstun.bill.port.messaging.out;

public record BillDeleted(
        Long id,
        Long sessionId,
        Long customerId) {
}

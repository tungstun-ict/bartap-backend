package com.tungstun.bill.port.messaging.out;

public record BillCreated(
        Long id,
        Long sessionId,
        Long customerId) {
}

package com.tungstun.bill.application.bill.event;

public record BillCreated(
        Long id,
        Long barId,
        Long sessionId,
        Long customerId) {
}

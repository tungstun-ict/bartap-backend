package com.tungstun.bill.application.bill.event;

public record BillDeleted(
        Long id,
        Long barId,
        Long sessionId,
        Long customerId) {
}

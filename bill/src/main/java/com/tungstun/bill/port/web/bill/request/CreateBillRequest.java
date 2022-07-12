package com.tungstun.bill.port.web.bill.request;

public record CreateBillRequest(
        Long sessionId,
        Long customerId,
        Long barId) {
}

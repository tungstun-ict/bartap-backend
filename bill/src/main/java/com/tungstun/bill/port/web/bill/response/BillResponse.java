package com.tungstun.bill.port.web.bill.response;

import com.tungstun.bill.domain.bill.Bill;

public record BillResponse(
        Long id,
        Long sessionId,
        Long customerId,
        Boolean isPayed) {

    public static BillResponse from(Bill bill) {
        return new BillResponse(bill.getId(), bill.getSessionId(), bill.getCustomerId(), bill.isPayed());
    }
}

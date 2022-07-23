package com.tungstun.bill.port.web.bill.response;

import com.tungstun.bill.domain.bill.Bill;

public record BillSummaryResponse(
        Long id,
        Long sessionId,
        Long customerId,
        Boolean isPayed) {

    public static BillSummaryResponse from(Bill bill) {
        return new BillSummaryResponse(
                bill.getId(),
                bill.getSessionId(),
                bill.getCustomer().getId(),
                bill.isPayed()
        );
    }
}

package com.tungstun.bill.port.web.bill.response;

import com.tungstun.bill.domain.bill.Bill;

import java.util.List;

public record BillResponse(
        Long id,
        Long sessionId,
        Long customerId,
        Boolean isPayed,
        List<OrderResponse> orders) {

    public static BillResponse from(Bill bill) {
        List<OrderResponse> orders = bill.getOrders()
                .parallelStream()
                .map(OrderResponse::from)
                .toList();

        return new BillResponse(
                bill.getId(),
                bill.getSessionId(),
                bill.getCustomer().getId(),
                bill.isPayed(),
                orders
        );
    }
}

package com.tungstun.bill.port.web.order.request;

public record AddOrderRequest(
        Long productId,
        Integer amount) {
}

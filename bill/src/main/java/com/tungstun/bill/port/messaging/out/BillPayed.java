package com.tungstun.bill.port.messaging.out;

public record BillPayed(
        Long id,
        Long barId,
        Boolean isPayed) {
}

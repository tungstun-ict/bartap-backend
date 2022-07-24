package com.tungstun.bill.application.bill.event;

public record BillPayed(
        Long id,
        Long barId,
        Boolean isPayed) {
}

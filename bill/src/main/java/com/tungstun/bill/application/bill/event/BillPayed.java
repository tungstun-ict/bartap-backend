package com.tungstun.bill.application.bill.event;

import com.tungstun.common.messaging.Event;

@Event
public record BillPayed(
        Long id,
        Long barId,
        Boolean isPayed) {
}

package com.tungstun.bill.port.messaging.in.product.message;

import com.tungstun.common.messaging.Event;

@Event
public record ProductDeleted(Long id) {
}

package com.tungstun.bill.port.web.order.response;

import com.tungstun.bill.domain.bill.OrderHistoryEntry;
import com.tungstun.bill.port.web.person.response.PersonResponse;

import java.time.LocalDateTime;

public record OrderHistoryEntryResponse(
        Long id,
        LocalDateTime date,
        String productName,
        Integer amount,
        PersonResponse customer,
        PersonResponse bartender) {

    public static OrderHistoryEntryResponse from(OrderHistoryEntry entry) {
        return new OrderHistoryEntryResponse(
                entry.getId(),
                entry.getDate(),
                entry.getProductName(),
                entry.getAmount(),
                new PersonResponse(entry.getCustomer().getId(), entry.getCustomer().getUsername()),
                new PersonResponse(entry.getBartender().getId(), entry.getBartender().getUsername())
        );
    }
}

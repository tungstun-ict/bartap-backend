package com.tungstun.bill.port.web.bill.response;

import com.tungstun.bill.domain.bill.Order;
import com.tungstun.bill.port.web.person.response.PersonResponse;
import com.tungstun.bill.port.web.product.response.ProductResponse;

import java.time.LocalDateTime;

public record OrderResponse(
        Long id,
        Integer amount,
        LocalDateTime creationDate,
        ProductResponse product,
        PersonResponse bartender) {

    public static OrderResponse from(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getAmount(),
                order.getCreationDate(),
                ProductResponse.from(order.getProduct()),
                new PersonResponse(
                        order.getBartender().getPersonId(),
                        order.getBartender().getUsername()
                )
        );
    }
}

package com.tungstun.bill.port.web.order;

import com.tungstun.bill.application.bill.BillCommandHandler;
import com.tungstun.bill.application.bill.BillQueryHandler;
import com.tungstun.bill.application.bill.command.AddOrder;
import com.tungstun.bill.application.bill.command.RemoveOrder;
import com.tungstun.bill.application.bill.query.ListOrderHistory;
import com.tungstun.bill.application.bill.query.ListOrdersOfBill;
import com.tungstun.bill.port.web.order.request.AddOrderRequest;
import com.tungstun.bill.port.web.order.response.OrderHistoryEntryResponse;
import com.tungstun.bill.port.web.order.response.OrderResponse;
import com.tungstun.common.security.BartapUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bars/{barId}/bills/{billId}")
public class OrderController {
    private final BillQueryHandler queryHandler;
    private final BillCommandHandler commandHandler;

    public OrderController(BillQueryHandler queryHandler, BillCommandHandler commandHandler) {
        this.queryHandler = queryHandler;
        this.commandHandler = commandHandler;
    }

    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Add order to bill",
            description = "A new order is created in the bill of the customer with the given id for the session with the provided id",
            tags = "Order"
    )
    public void addOrder(
            @PathVariable("barId") Long barId,
            @PathVariable("billId") Long billId,
            @RequestBody AddOrderRequest request,
            Authentication authentication
    ) { //TODO: Does not return id, thus not in any way rest compliant
        BartapUserDetails userDetails = (BartapUserDetails) authentication.getPrincipal();
        commandHandler.handle(new AddOrder(
                billId,
                barId,
                userDetails.getId(),
                request.productId(),
                request.amount())
        );
    }

    @DeleteMapping("/orders/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Remove order from bill",
            description = "The order is removed from the bill of the customer with the given id for the session with the provided id",
            tags = "Order"
    )
    public void removeOrder(
            @PathVariable("barId") Long barId,
            @PathVariable("billId") Long billId,
            @PathVariable("orderId") Long orderId,
            Authentication authentication
    ) {
        BartapUserDetails userDetails = (BartapUserDetails) authentication.getPrincipal();
        commandHandler.handle(new RemoveOrder(
                billId,
                barId,
                userDetails.getId(),
                orderId)
        );
    }

    @GetMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get orders from bill",
            description = "The orders from the bill of the customer with the given id for the session with the provided id is returned",
            tags = "Order"
    )
    public List<OrderResponse> getOrders(
            @PathVariable("barId") Long barId,
            @PathVariable("billId") Long billId
    ) {
        return queryHandler.handle(new ListOrdersOfBill(billId, barId))
                .parallelStream()
                .map(OrderResponse::from)
                .toList();
    }

    @GetMapping("/order-history")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get order history from bill",
            description = "The order history of the bill with the provided id is returned",
            tags = "Order"
    )
    public List<OrderHistoryEntryResponse> getOrderHistory(
            @PathVariable("barId") Long barId,
            @PathVariable("billId") Long billId
    ) {
        return queryHandler.handle(new ListOrderHistory(billId, barId))
                .parallelStream()
                .map(OrderHistoryEntryResponse::from)
                .toList();
    }
}

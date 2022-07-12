package com.tungstun.bill.port.web.bill;

import com.tungstun.bill.application.bill.BillCommandHandler;
import com.tungstun.bill.application.bill.BillQueryHandler;
import com.tungstun.bill.application.bill.command.CreateBill;
import com.tungstun.bill.application.bill.command.DeleteBill;
import com.tungstun.bill.application.bill.command.UpdateBillPayed;
import com.tungstun.bill.application.bill.query.GetBill;
import com.tungstun.bill.application.bill.query.ListBillsOfPerson;
import com.tungstun.bill.application.bill.query.ListBillsOfSession;
import com.tungstun.bill.domain.bill.Bill;
import com.tungstun.bill.port.web.bill.request.CreateBillRequest;
import com.tungstun.bill.port.web.bill.request.UpdateBillPayedRequest;
import com.tungstun.bill.port.web.bill.response.BillIdResponse;
import com.tungstun.bill.port.web.bill.response.BillResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bars/{barId}/bills")
public class BillController {
    private final BillQueryHandler queryHandler;
    private final BillCommandHandler commandHandler;

    public BillController(BillQueryHandler queryHandler, BillCommandHandler commandHandler) {
        this.queryHandler = queryHandler;
        this.commandHandler = commandHandler;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create a new bill",
            description = "A new bill is created for the customer with the given id for the session with the provided id",
            tags = "Bill"
    )
    public BillIdResponse createBill(
            @PathVariable("barId") Long barId,
            @RequestBody CreateBillRequest request) {
        Long id = commandHandler.handle(new CreateBill(request.sessionId(), request.customerId(), barId));
        return new BillIdResponse(id);
    }

    @PutMapping("/{billId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Update bill",
            description = "The isPayed status of the bill with the given id is updated to the boolean value provided in the request body",
            tags = "Bill"
    )
    public BillIdResponse updateBill(
            @PathVariable("barId") Long barId,
            @PathVariable("billId") Long id,
            @RequestBody UpdateBillPayedRequest request) {
        commandHandler.handle(new UpdateBillPayed(id, barId, request.payed()));
        return new BillIdResponse(id);
    }

    @DeleteMapping("/{billId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete bill",
            description = "The bill with given id is deleted",
            tags = "Bill"
    )
    public void deleteBill(
            @PathVariable("barId") Long barId,
            @PathVariable("billId") Long id) {
        commandHandler.handle(new DeleteBill(id, barId));
    }

    @GetMapping("/{billId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get bill",
            description = "The bill with given id is queried",
            tags = "Bill"
    )
    public BillResponse getBill(
            @PathVariable("barId") Long barId,
            @PathVariable("billId") Long id) {
        Bill bill = queryHandler.handle(new GetBill(id, barId));
        return BillResponse.from(bill);
    }

    @GetMapping("/session/{sessionId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get bills of session",
            description = "The bills of session with given id are queried",
            tags = "Bill"
    )
    public List<BillResponse> getBillsOfSession(
            @PathVariable("barId") Long barId,
            @PathVariable("sessionId") Long id) {
        List<Bill> bills = queryHandler.handle(new ListBillsOfSession(id, barId));
        return bills.parallelStream()
                .map(BillResponse::from)
                .toList();
    }

    @GetMapping("/customer/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get bills of customer",
            description = "The bills of customer with given id are queried",
            tags = "Bill"
    )
    public List<BillResponse> getBillsOfCustomer(
            @PathVariable("barId") Long barId,
            @PathVariable("customerId") Long id) {
        List<Bill> bills = queryHandler.handle(new ListBillsOfPerson(id, barId));
        return bills.parallelStream()
                .map(BillResponse::from)
                .toList();
    }
}

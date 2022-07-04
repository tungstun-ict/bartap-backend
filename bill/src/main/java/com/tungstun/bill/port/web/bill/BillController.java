package com.tungstun.bill.port.web.bill;

import com.tungstun.bill.application.bill.BillCommandHandler;
import com.tungstun.bill.application.bill.BillQueryHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bills")
public class BillController {
    private final BillQueryHandler queryHandler;
    private final BillCommandHandler commandHandler;

    public BillController(BillQueryHandler queryHandler, BillCommandHandler commandHandler) {
        this.queryHandler = queryHandler;
        this.commandHandler = commandHandler;
    }

    @GetMapping
    public String getHello() {
        return "Hello World";
    }

}

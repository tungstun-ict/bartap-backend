package com.tungstun.bill.port.web.bill.request;

import javax.validation.constraints.NotNull;

public record UpdateBillPayedRequest(
        @NotNull(message = "Boolean payed cannot be null")
        Boolean payed) {
}

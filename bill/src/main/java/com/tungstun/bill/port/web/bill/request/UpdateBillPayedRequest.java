package com.tungstun.bill.port.web.bill.request;

public record UpdateBillPayedRequest(Long barId, Boolean payed) {
}

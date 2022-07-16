package com.tungstun.core.application.session.command;

public record RemoveBill(
        Long barId,
        Long sessionId,
        Long billId) {
}

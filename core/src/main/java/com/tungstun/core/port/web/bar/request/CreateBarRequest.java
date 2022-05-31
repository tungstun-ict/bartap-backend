package com.tungstun.core.port.web.bar.request;

public record CreateBarRequest(
        String name,
        String address,
        String mail,
        String phoneNumber) {
}

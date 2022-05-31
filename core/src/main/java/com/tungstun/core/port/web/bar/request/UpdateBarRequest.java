package com.tungstun.core.port.web.bar.request;

public record UpdateBarRequest(
        String name,
        String address,
        String mail,
        String phoneNumber) {
}

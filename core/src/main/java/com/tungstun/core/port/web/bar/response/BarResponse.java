package com.tungstun.core.port.web.bar.response;

public record BarResponse(
        Long id,
        String name,
        String address,
        String mail,
        String phoneNumber) {
}

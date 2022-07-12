package com.tungstun.core.port.web.bar.response;

import com.tungstun.core.domain.bar.Bar;

public record BarResponse(
        Long id,
        String name,
        String address,
        String mail,
        String phoneNumber) {
    public static BarResponse from (Bar bar) {
        return new BarResponse(bar.getId(), bar.getName(), bar.getAddress(), bar.getMail(), bar.getPhoneNumber());
    }
}

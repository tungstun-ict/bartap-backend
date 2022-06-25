package com.tungstun.security.port.messaging.out.message;

public record UserCreated(Long id,
                          String username) {
}
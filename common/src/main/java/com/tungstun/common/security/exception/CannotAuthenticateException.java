package com.tungstun.common.security.exception;

import org.springframework.security.core.AuthenticationException;

public class CannotAuthenticateException extends AuthenticationException {
    public CannotAuthenticateException(String msg) {
        super(msg);
    }
}

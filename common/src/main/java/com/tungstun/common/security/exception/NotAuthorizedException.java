package com.tungstun.common.security.exception;


import org.springframework.security.access.AccessDeniedException;

public class NotAuthorizedException extends AccessDeniedException {

    public NotAuthorizedException(String msg) {
        super(msg);
    }

    public NotAuthorizedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

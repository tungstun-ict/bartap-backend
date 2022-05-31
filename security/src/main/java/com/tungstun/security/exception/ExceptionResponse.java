package com.tungstun.security.exception;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

public record ExceptionResponse(
        ZonedDateTime timestamp,
        String path,
        String message,
        List<String> errors
) {
    private static String getCurrentHttpRequestUri() {
        return RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes servletRequestAttributes ?
                servletRequestAttributes.getRequest().getRequestURI()
                : "";
    }

    public static ExceptionResponse with(String message, List<String> errors) {
        return new ExceptionResponse(
                ZonedDateTime.now(),
                getCurrentHttpRequestUri(),
                message,
                errors
        );
    }

    public static ExceptionResponse with(String message, String error) {
        return with(message, Collections.singletonList(error));
    }

}

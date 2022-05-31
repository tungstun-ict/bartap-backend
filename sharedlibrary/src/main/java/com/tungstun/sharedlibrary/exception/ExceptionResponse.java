package com.tungstun.sharedlibrary.exception;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

/**
 * Response record used as response when exceptions are thrown.<br>
 * Record contains time of exception handling, called path, the message and thrown errors<br>
 * */
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

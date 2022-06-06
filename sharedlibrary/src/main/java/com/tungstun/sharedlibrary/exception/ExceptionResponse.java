package com.tungstun.sharedlibrary.exception;

import org.springframework.web.context.request.RequestAttributes;
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
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes servletRequestAttributes) {
            return servletRequestAttributes.getRequest().getRequestURI();
        }
        return "";
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

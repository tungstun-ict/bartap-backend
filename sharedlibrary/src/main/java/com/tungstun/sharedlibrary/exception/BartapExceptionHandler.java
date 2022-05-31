package com.tungstun.sharedlibrary.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;


/**
 * Base exception handler class for bartap cluster
 * */
@ControllerAdvice
@RestController
public class BartapExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {ConstraintViolationException.class,})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse mainHandler(ConstraintViolationException e) {
        List<String> violationMessages = e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .toList();
        return ExceptionResponse.with("Incorrect input", violationMessages);
    }

    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse handleRuntimeException(RuntimeException e) {
        e.printStackTrace();
        return ExceptionResponse.with("Unexpected error", e.getMessage());
    }
}

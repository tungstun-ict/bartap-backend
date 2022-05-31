package com.tungstun.security.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
@RestController
public class SecurityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ConstraintViolationException.class,})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse mainHandler(ConstraintViolationException e) {
        List<String> violationMessages = e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .toList();
        return ExceptionResponse.with("Incorrect input", violationMessages);
    }

    @ExceptionHandler(value = {JWTVerificationException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionResponse mainHandler(JWTVerificationException e) {
        return ExceptionResponse.with("Incorrect input", e.getMessage());
    }

    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse handleRuntimeException(RuntimeException e) {
        e.printStackTrace();
        return ExceptionResponse.with("Unexpected error", e.getMessage());
    }
}

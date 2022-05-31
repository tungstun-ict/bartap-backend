package com.tungstun.security.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.tungstun.sharedlibrary.exception.BartapExceptionHandler;
import com.tungstun.sharedlibrary.exception.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class SecurityExceptionHandler extends BartapExceptionHandler {
    @ExceptionHandler(value = {JWTVerificationException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionResponse mainHandler(JWTVerificationException e) {
        return ExceptionResponse.with("Incorrect input", e.getMessage());
    }
}

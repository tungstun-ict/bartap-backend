package com.tungstun.security.exception;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.tungstun.common.exception.BartapExceptionHandler;
import com.tungstun.common.exception.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class SecurityExceptionHandler extends BartapExceptionHandler {
    @ExceptionHandler(value = {JWTCreationException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse handleJWTCreationException(JWTCreationException e) {
        return ExceptionResponse.with("Error occurred during token creation", e.getMessage());
    }
}

package com.tungstun.common.exception;

import com.tungstun.common.security.exception.NotAuthenticatedException;
import com.tungstun.common.security.exception.NotAuthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;

/**
 * Base exception handler class for bartap services<br>
 * This handler handles all basic and shared exceptions that could be thrown.<br>
 * This class can be extended to add extra Service specific Custom Exception handles<br>
 */
@ControllerAdvice
@RestController
public class BartapExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(BartapExceptionHandler.class);

    @ExceptionHandler(value = {UserNotFoundException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse handleUserNotFoundException(UserNotFoundException e) {
        return ExceptionResponse.with("User not found", "No user exists with given username");
    }

    @ExceptionHandler(value = {NotAuthenticatedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionResponse handleNotAuthorizedExceptions(NotAuthenticatedException e) {
        e.printStackTrace();
        return ExceptionResponse.with("User not authenticated", e.getLocalizedMessage());
    }

    @ExceptionHandler(value = {NotAuthorizedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse handleNotAuthorizedExceptions(NotAuthorizedException e) {
        e.printStackTrace();
        return ExceptionResponse.with("User not authorized for action or resource", e.getLocalizedMessage());
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse handleConstraintViolations(ConstraintViolationException e) {
        List<String> violationMessages = e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .toList();
        return ExceptionResponse.with("Incorrect input", violationMessages);
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse handleConstraintViolations(DataIntegrityViolationException e) {
        String stackTrace = Arrays.toString(e.getStackTrace());
        LOG.error(stackTrace);
        return ExceptionResponse.with("Incorrect input", List.of("Something went wrong during persistence"));
    }

    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse handleRuntimeException(RuntimeException e) {
        e.printStackTrace();
        return ExceptionResponse.with("Unexpected error", e.getMessage());
    }
}

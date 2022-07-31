package com.tungstun.gateway.swagger;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception to be thrown if a requested service OpenApi Definition does not exist or something went wrong during it's retrieval.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ServiceDefinitionResourceNotFoundException extends RuntimeException {
}

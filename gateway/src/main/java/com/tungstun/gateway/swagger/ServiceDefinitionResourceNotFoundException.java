package com.tungstun.gateway.swagger;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ServiceDefinitionResourceNotFoundException extends RuntimeException {
}

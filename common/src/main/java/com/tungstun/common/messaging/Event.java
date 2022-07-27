package com.tungstun.common.messaging;

import org.springframework.context.annotation.Bean;

import java.lang.annotation.*;

/**
 * Methods annotated with {@code @Event} are registered for serialization and deserialization of the class.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Bean
public @interface Event {
}

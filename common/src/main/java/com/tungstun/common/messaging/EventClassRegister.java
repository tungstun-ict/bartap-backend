package com.tungstun.common.messaging;

import org.reflections.Reflections;
import org.springframework.kafka.annotation.KafkaHandler;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Static function class that contains a set of all classes used as event DTO's.
 */
public class EventClassRegister {
    private static Set<Class<?>> eventClasses;

    /**
     * Reflectively gets and registers all event DTO classes if eventClasses has not been initialized yet.
     * Method gets all classes annotated with {@code @Event} and all parameter classes annotated with {@code @KafkaHandler}
     *
     * @return Set of event classes.
     */
    public static Set<Class<?>> getEventClasses() {
        if (eventClasses == null) {
            eventClasses = new Reflections("com.tungstun")
                    .getTypesAnnotatedWith(Event.class);

            new Reflections("com.tungstun")
                    .getSubTypesOf(KafkaMessageConsumer.class)
                    .parallelStream()
                    .flatMap(consumerClass -> Stream.of(consumerClass.getDeclaredMethods()))
                    .filter(consumerMethod -> Arrays
                            .stream(consumerMethod.getDeclaredAnnotations())
                            .map(Annotation::annotationType)
                            .anyMatch(annotation -> annotation.equals(KafkaHandler.class)))
                    .flatMap(consumerMethod -> Stream.of(consumerMethod.getParameterTypes()))
                    .forEach(eventClasses::add);
        }
        return Collections.unmodifiableSet(eventClasses);
    }

    private EventClassRegister() {
        // Static class, there is no reason to construct an instance
    }
}
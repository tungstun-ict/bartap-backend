package com.tungstun.gateway.swagger;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * <p>Code adapted from <a href="https://github.com/GnanaJeyam/microservice-patterns">https://github.com/GnanaJeyam/microservice-patterns</a></p>
 * <p>
 * Singleton class that stores an In-Memory map with service id's paired with their OpenApi Definition JSON String
 */
@Component
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ServiceDefinitionsContext {
    private ConcurrentMap<String, String> serviceDefinitions;

    private ServiceDefinitionsContext() {
        this.serviceDefinitions = new ConcurrentHashMap<>();
    }

    /**
     * Changes the {@code serviceDefinitions} ConcurrentMap to the provided ConcurrentMap if it is not {@code null}.
     */
    public void updateServiceDefinitions(ConcurrentMap<String, String> map) {
        if (map != null) serviceDefinitions = map;
    }

    public String getSwaggerDefinition(String serviceId) {
        return this.serviceDefinitions.get(serviceId);
    }

    public List<String> getServiceIds() {
        return new ArrayList<>(serviceDefinitions.keySet());
    }
}

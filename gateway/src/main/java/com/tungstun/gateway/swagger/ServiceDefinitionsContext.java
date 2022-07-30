package com.tungstun.gateway.swagger;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author satish sharma
 * <pre>
 *   	In-Memory store to hold API-Definition JSON
 * </pre>
 */
@Component
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ServiceDefinitionsContext {
    private final ConcurrentMap<String, String> serviceDefinitions;

    private ServiceDefinitionsContext() {
        this.serviceDefinitions = new ConcurrentHashMap<>();
    }

    public void updateServiceDefinitions(Map<String, String> map) {
        serviceDefinitions.clear();
        serviceDefinitions.putAll(map);
    }

    public String getSwaggerDefinition(String serviceId) {
        return this.serviceDefinitions.get(serviceId);
    }

    public List<String> getServiceIds() {
        return new ArrayList<>(serviceDefinitions.keySet());
    }
}

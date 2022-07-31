package com.tungstun.gateway.swagger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.graalvm.collections.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * <p>Code adapted from <a href="https://github.com/GnanaJeyam/microservice-patterns">https://github.com/GnanaJeyam/microservice-patterns</a></p>
 * <p>
 * Class that periodically updates the {@code ServiceDefinitionContext}.
 */
@Component
public class ServiceDefinitionsUpdater {
    private static final Logger LOG = LoggerFactory.getLogger(ServiceDefinitionsUpdater.class);
    private static final String DEFAULT_SWAGGER_URL = "/api/v3/api-docs";

    private final ServiceDefinitionsContext definitionContext;
    private final DiscoveryClient discoveryClient;
    private final RestTemplate template;

    @Value("${spring.application.name}")
    private String applicationName;

    public ServiceDefinitionsUpdater(ServiceDefinitionsContext definitionContext, DiscoveryClient discoveryClient) {
        this.definitionContext = definitionContext;
        this.discoveryClient = discoveryClient;
        this.template = new RestTemplate();
    }

    /**
     * Gets all running services from the service registry, polls their OpenApi Definition and updates the {@code ServiceDefinitionContext} with them.
     */
    @Scheduled(fixedDelayString = "${swagger.config.refreshrate}")
    public void refreshOpenApiDefinitions() {
        LOG.info("Starting Service Definitions Context refresh");

        ConcurrentMap<String, String> serviceDocumentations = discoveryClient.getServices()
                .parallelStream()
                .filter(serviceId -> !serviceId.equals(applicationName))
                .map(serviceId -> Pair.create(serviceId, getServiceDefinition(serviceId)))
                .filter(pair -> pair.getRight().isPresent())
                .collect(Collectors.toConcurrentMap(Pair::getLeft, pair -> pair.getRight().get()));
        definitionContext.updateServiceDefinitions(serviceDocumentations);

        LOG.info("Service Definitions Context Refreshed for at : {}", LocalDateTime.now());
    }

    /**
     * Gets a running service instance's URL and polls the OpenApi Definition from it.
     */
    private Optional<String> getServiceDefinition(String serviceId) {
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceId);
        if (serviceInstances == null || serviceInstances.isEmpty()) {
            LOG.info("No instances available for service : {} ", serviceId);
            return Optional.empty();
        }

        String swaggerURL = serviceInstances.get(0).getUri() + DEFAULT_SWAGGER_URL;
        Optional<String> jsonData = pollOpenApiDefinition(swaggerURL, serviceId);
        if (jsonData.isEmpty()) {
            LOG.error("Skipping Definition Refresh for service : {}", serviceId);
        }

        return jsonData;
    }

    /**
     * Polls the OpenApi Definition JSON from the provided url.
     */
    private Optional<String> pollOpenApiDefinition(String url, String serviceName) {
        try {
            Object data = template.getForObject(url, Object.class);
            String json = new ObjectMapper().writeValueAsString(data);
            return Optional.of(json);
        } catch (RestClientException | JsonProcessingException ex) {
            LOG.error("Error while getting service definition for service : {}. Error : {} ", serviceName, ex.getMessage());
            return Optional.empty();
        }
    }
}

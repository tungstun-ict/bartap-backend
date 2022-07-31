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
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author satish sharma
 * <pre>
 *   Periodically poll the service instaces and update the in memory store as key value pair
 * </pre>
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

    @Scheduled(fixedDelayString = "${swagger.config.refreshrate}")
    public void refreshSwaggerDefinitions() {
        LOG.info("Starting Service Definitions Context refresh");

        Map<String, String> serviceDocumentations = discoveryClient.getServices()
                .parallelStream()
                .filter(serviceId -> !serviceId.equals(applicationName))
                .map(serviceId -> Pair.create(serviceId, getServiceDefinition(serviceId)))
                .filter(pair -> pair.getRight().isPresent())
                .collect(Collectors.toMap(Pair::getLeft, pair -> pair.getRight().get()));
        definitionContext.updateServiceDefinitions(serviceDocumentations);

        LOG.info("Service Definitions Context Refreshed for at : {}", LocalDateTime.now());
    }

    private Optional<String> getServiceDefinition(String serviceId) {
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceId);
        if (serviceInstances == null || serviceInstances.isEmpty()) { //Should not be the case, kept for failsafe
            LOG.info("No instances available for service : {} ", serviceId);
            return Optional.empty();
        }

        String swaggerURL = serviceInstances.get(0).getUri() + DEFAULT_SWAGGER_URL;
        Optional<String> jsonData = loadSwaggerDefinition(swaggerURL, serviceId);
        if (jsonData.isEmpty()) {
            LOG.error("Skipping Definition Refresh for service : {}", serviceId);
        }

        return jsonData;
    }

    private Optional<String> loadSwaggerDefinition(String url, String serviceName) {
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

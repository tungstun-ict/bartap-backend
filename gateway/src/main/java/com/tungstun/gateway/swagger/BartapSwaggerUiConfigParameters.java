package com.tungstun.gateway.swagger;

import org.springdoc.core.SwaggerUiConfigParameters;
import org.springdoc.core.SwaggerUiConfigProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Primary
@Configuration
public class BartapSwaggerUiConfigParameters extends SwaggerUiConfigParameters {
    private static final String SERVICE_API_DOCS_URL_PREFIX = "/api/v3/api-docs/services/";

    private final ServiceDefinitionsContext definitionContext;

    public BartapSwaggerUiConfigParameters(SwaggerUiConfigProperties swaggerUiConfig, ServiceDefinitionsContext definitionContext) {
        super(swaggerUiConfig);
        this.definitionContext = definitionContext;
    }

    private SwaggerUrl toSwaggerUrl(String bartapServiceName) {
        String serviceName = bartapServiceName.replace("bartap-", "");
        String displayName = serviceName.substring(0, 1).toUpperCase() + serviceName.substring(1).toLowerCase();
        return new SwaggerUrl(bartapServiceName, SERVICE_API_DOCS_URL_PREFIX + bartapServiceName, displayName);
    }

    @Override
    public Map<String, Object> getConfigParameters() {
        List<SwaggerUrl> swaggerUrls = definitionContext.getServiceIds()
                .stream()
                .map(this::toSwaggerUrl)
                .collect(toList());

        Map<String, Object> configParameters = super.getConfigParameters();
        swaggerUrls.add(new SwaggerUrl("Gateway", (String) configParameters.get(URL_PROPERTY), "Gateway"));
        configParameters.put(URLS_PROPERTY, swaggerUrls);
        return configParameters;
    }
}

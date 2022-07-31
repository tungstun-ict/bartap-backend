package com.tungstun.gateway.swagger;

import org.apache.commons.lang.WordUtils;
import org.springdoc.core.SwaggerUiConfigParameters;
import org.springdoc.core.SwaggerUiConfigProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * Class extending {@code SwaggerUiConfigParameters} to override the config parameters used by swagger UI.
 */
@Primary
@Configuration
public class BartapSwaggerUiConfigParameters extends SwaggerUiConfigParameters {
    private static final String SERVICE_API_DOCS_URL_PREFIX = "/api/v3/api-docs/services/";

    private final ServiceDefinitionsContext definitionContext;
    private final String applicationName;

    public BartapSwaggerUiConfigParameters(
            SwaggerUiConfigProperties swaggerUiConfig,
            ServiceDefinitionsContext definitionContext,
            @Value("${spring.application.name:Application}") String applicationName
    ) {
        super(swaggerUiConfig);
        this.definitionContext = definitionContext;
        this.applicationName = applicationName;
    }

    /**
     * Calls the original {@code getConfigParameters()} method to get the config parameters and overrides the {@code urls} value with a custom list of {@code SwaggerUrl}.
     * The swagger urls contain the currently cached service names with a link that Swagger UI uses to retrieve their OpenApi Definition.
     */
    @Override
    public Map<String, Object> getConfigParameters() {
        List<SwaggerUrl> swaggerUrls = definitionContext.getServiceIds()
                .stream()
                .map(this::toSwaggerUrl)
                .collect(toList());

        Map<String, Object> configParameters = super.getConfigParameters();
        swaggerUrls.add(new SwaggerUrl(applicationName, (String) configParameters.get(URL_PROPERTY), WordUtils.capitalize(applicationName)));
        configParameters.put(URLS_PROPERTY, swaggerUrls);
        return configParameters;
    }

    private SwaggerUrl toSwaggerUrl(String serviceName) {
        return new SwaggerUrl(serviceName, SERVICE_API_DOCS_URL_PREFIX + serviceName, WordUtils.capitalize(serviceName));
    }

}

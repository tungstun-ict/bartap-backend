package com.tungstun.gateway.locations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LocationConfig {
    @Value("${com.tungstun.locations.stock:http://localhost:8080/api}")
    protected String bartapApiUrl;
    @Value("${com.tungstun.locations.stock:http://localhost:8080/swagger}")
    protected String bartapSwaggerUrl;
//    @Value("${com.tungstun.locations.stock:http://httpbin.org:80}")
//    protected String stockUri;
//    @Value("${com.tungstun.locations.stock:https://google.com}")
//    protected String googleUri;
}

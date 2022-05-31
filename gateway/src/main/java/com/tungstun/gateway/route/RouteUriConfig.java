package com.tungstun.gateway.route;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "com.tungstun.bartap.routes")
public class RouteUriConfig {
    private String bartap;
    private String swagger;

    public String getBartap() {
        return bartap;
    }

    public void setBartap(String bartap) {
        this.bartap = bartap;
    }

    public String getSwagger() {
        return swagger;
    }

    public void setSwagger(String swagger) {
        this.swagger = swagger;
    }
}

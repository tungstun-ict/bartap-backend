package com.tungstun.common.locale;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.time.ZoneId;
import java.util.TimeZone;

@Configuration
@ConfigurationProperties(prefix = "com.tungstun.bartap.locale")
public class TimeZoneConfig {
    private String timezone = "Europe/Amsterdam";

    @PostConstruct
    private void loadTimezoneConfig() {
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of(timezone)));
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
}

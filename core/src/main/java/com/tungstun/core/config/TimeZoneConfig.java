package com.tungstun.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.time.ZoneId;
import java.util.TimeZone;

@Configuration
public class TimeZoneConfig {
    public TimeZoneConfig(@Value("${com.tungstun.bartap.timeZone}") String timeZone) {
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of(timeZone)));
    }
}

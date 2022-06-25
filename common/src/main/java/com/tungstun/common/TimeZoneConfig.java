package com.tungstun.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.time.ZoneId;
import java.util.TimeZone;

@Configuration
public class TimeZoneConfig {
    public static final String DEFAULT = "Europe/Amsterdam";

    public TimeZoneConfig(@Value("${com.tungstun.bartap.timeZone:#{T(com.tungstun.common.TimeZoneConfig).DEFAULT}}") String timeZone) {
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of(timeZone)));
    }
}

package com.tungstun.sharedlibrary;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.time.ZoneId;
import java.util.TimeZone;

@Configuration
public class TimeZoneConfig {
    public static final String DEFAULT = "Europe/Amsterdam";

    public TimeZoneConfig(@Value("${com.tungstun.bartap.timeZone:#{T(com.tungstun.sharedlibrary.TimeZoneConfig).DEFAULT}}") String timeZone) {
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of(timeZone)));
    }
}

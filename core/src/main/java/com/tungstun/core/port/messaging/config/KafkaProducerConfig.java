package com.tungstun.core.port.messaging.config;

import com.tungstun.common.messaging.KafkaConfigBase;
import com.tungstun.common.messaging.KafkaMessageProducer;
import com.tungstun.core.application.session.event.SessionCreated;
import com.tungstun.core.application.session.event.SessionDeleted;
import com.tungstun.core.application.session.event.SessionEnded;
import com.tungstun.core.application.session.event.SessionLocked;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class KafkaProducerConfig extends KafkaConfigBase {
    private static final String TOPIC = "core";
    private static final Set<Class<?>> CLASSES = Set.of(
            SessionCreated.class,
            SessionDeleted.class,
            SessionEnded.class,
            SessionLocked.class
    );

    @Bean
    public NewTopic core() {
        return new NewTopic(TOPIC, 1, (short) 1);
    }

    @Bean
    public KafkaMessageProducer kafkaMessageProducer() {
        return new KafkaMessageProducer(TOPIC, defaultKafkaTemplate(CLASSES));
    }
}
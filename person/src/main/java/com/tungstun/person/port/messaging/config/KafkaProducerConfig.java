package com.tungstun.person.port.messaging.config;

import com.tungstun.common.messaging.KafkaConfigBase;
import com.tungstun.common.messaging.KafkaMessageProducer;
import com.tungstun.person.application.person.event.PersonCreated;
import com.tungstun.person.application.person.event.PersonDeleted;
import com.tungstun.person.application.person.event.PersonUpdated;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class KafkaProducerConfig extends KafkaConfigBase {
    private static final String TOPIC = "person";
    private static final Set<Class<?>> CLASSES = Set.of(
            PersonCreated.class,
            PersonUpdated.class,
            PersonDeleted.class
    );

    @Bean
    public NewTopic person() {
        return new NewTopic(TOPIC, 1, (short) 1);
    }

    @Bean
    public KafkaMessageProducer kafkaMessageProducer() {
        return new KafkaMessageProducer(TOPIC, defaultKafkaTemplate(CLASSES));
    }
}

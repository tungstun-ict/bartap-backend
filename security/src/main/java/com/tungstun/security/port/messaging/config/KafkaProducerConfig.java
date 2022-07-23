package com.tungstun.security.port.messaging.config;

import com.tungstun.common.messaging.KafkaConfigBase;
import com.tungstun.common.messaging.KafkaMessageProducer;
import com.tungstun.security.application.user.event.NameUpdated;
import com.tungstun.security.port.messaging.out.message.UserCreated;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class KafkaProducerConfig extends KafkaConfigBase {
    private static final String TOPIC = "security";
    private static final Set<Class<?>> CLASSES = Set.of(
            UserCreated.class,
            NameUpdated.class
    );

    @Bean
    public NewTopic security() {
        return new NewTopic(TOPIC, 1, (short) 1);
    }

    @Bean
    public KafkaMessageProducer kafkaMessageProducer() {
        return new KafkaMessageProducer(TOPIC, defaultKafkaTemplate(CLASSES));
    }
}

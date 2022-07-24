package com.tungstun.product.port.messaging.config;

import com.tungstun.common.messaging.KafkaConfigBase;
import com.tungstun.common.messaging.KafkaMessageProducer;
import com.tungstun.product.application.product.event.ProductCreated;
import com.tungstun.product.application.product.event.ProductDeleted;
import com.tungstun.product.application.product.event.ProductUpdated;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class KafkaProducerConfig extends KafkaConfigBase {
    private static final String TOPIC = "product";
    private static final Set<Class<?>> CLASSES = Set.of(
            ProductCreated.class,
            ProductUpdated.class,
            ProductDeleted.class
    );

    @Bean
    public NewTopic product() {
        return new NewTopic(TOPIC, 1, (short) 1);
    }

    @Bean
    public KafkaMessageProducer kafkaMessageProducer() {
        return new KafkaMessageProducer(TOPIC, defaultKafkaTemplate(CLASSES));
    }
}

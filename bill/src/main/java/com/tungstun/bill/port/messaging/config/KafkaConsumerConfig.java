package com.tungstun.bill.port.messaging.config;


import com.tungstun.bill.port.messaging.in.person.message.PersonCreated;
import com.tungstun.bill.port.messaging.in.person.message.PersonDeleted;
import com.tungstun.bill.port.messaging.in.person.message.PersonUpdated;
import com.tungstun.bill.port.messaging.in.product.message.ProductCreated;
import com.tungstun.bill.port.messaging.in.product.message.ProductDeleted;
import com.tungstun.bill.port.messaging.in.product.message.ProductUpdated;
import com.tungstun.common.messaging.KafkaConfigBase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.listener.CommonLoggingErrorHandler;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.Set;

@Configuration
public class KafkaConsumerConfig extends KafkaConfigBase {
    private static final Set<Class<?>> CLASSES = Set.of(
            ProductCreated.class,
            ProductUpdated.class,
            ProductDeleted.class,
            PersonCreated.class,
            PersonUpdated.class,
            PersonDeleted.class
    );

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>>
    kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setCommonErrorHandler(new CommonLoggingErrorHandler());
        factory.setConsumerFactory(defaultConsumerFactory(CLASSES));
        return factory;
    }
}

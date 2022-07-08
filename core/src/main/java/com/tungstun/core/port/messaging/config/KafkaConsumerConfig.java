package com.tungstun.core.port.messaging.config;

import com.tungstun.common.messaging.KafkaConfigBase;
import com.tungstun.core.port.messaging.in.bill.message.BillCreated;
import com.tungstun.core.port.messaging.in.bill.message.BillDeleted;
import com.tungstun.core.port.messaging.in.security.messages.UserCreated;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.listener.CommonLoggingErrorHandler;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.Set;

@Configuration
public class KafkaConsumerConfig extends KafkaConfigBase {
    private static final String CONSUMING_TOPIC = "bill";
    private static final Set<Class<?>> CLASSES = Set.of(
            BillCreated.class,
            BillDeleted.class,
            UserCreated.class
    );

    @Bean
    public NewTopic createNewTopic() {
        return new NewTopic(CONSUMING_TOPIC, 1, (short) 1);
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>>
    kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setCommonErrorHandler(new CommonLoggingErrorHandler());
        factory.setConsumerFactory(defaultConsumerFactory(CLASSES));
        return factory;
    }
}

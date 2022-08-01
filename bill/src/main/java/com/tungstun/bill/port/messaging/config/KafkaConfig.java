package com.tungstun.bill.port.messaging.config;


import com.tungstun.common.messaging.KafkaConfigBase;
import com.tungstun.common.messaging.KafkaMessageProducer;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig extends KafkaConfigBase {
    private static final String TOPIC = "bill";

    @Bean
    public NewTopic bill() {
        return new NewTopic(TOPIC, 1, (short) 1);
    }

    @Bean
    public KafkaMessageProducer kafkaMessageProducer() {
        return createMessageProducer(TOPIC);
    }
}

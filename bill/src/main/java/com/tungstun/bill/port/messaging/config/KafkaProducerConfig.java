package com.tungstun.bill.port.messaging.config;

import com.tungstun.bill.application.bill.event.BillCreated;
import com.tungstun.bill.application.bill.event.BillDeleted;
import com.tungstun.bill.application.bill.event.BillPayed;
import com.tungstun.common.messaging.KafkaConfigBase;
import com.tungstun.common.messaging.KafkaMessageProducer;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class KafkaProducerConfig extends KafkaConfigBase {
    private static final String TOPIC = "bill";
    private static final Set<Class<?>> CLASSES = Set.of(
            BillCreated.class,
            BillPayed.class,
            BillDeleted.class
    );

    @Bean
    public NewTopic bill() {
        return new NewTopic(TOPIC, 1, (short) 1);
    }

    @Bean
    public KafkaMessageProducer kafkaMessageProducer() {
        return new KafkaMessageProducer(TOPIC, defaultKafkaTemplate(CLASSES));
    }
}

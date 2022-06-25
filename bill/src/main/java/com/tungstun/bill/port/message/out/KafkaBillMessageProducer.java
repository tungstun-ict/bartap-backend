package com.tungstun.bill.port.message.out;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaBillMessageProducer {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaBillMessageProducer.class);
    private static final String TOPIC = "bill";

    private final KafkaTemplate<String, Object> template;

    public KafkaBillMessageProducer(KafkaTemplate<String, Object> template) {
        this.template = template;
    }

    public void publish(String id, Object data) {
        template.send(TOPIC, id, data)
                .addCallback(
                        success -> LOG.info("Published message {} with id {}", data, id),
                        failure -> LOG.warn("Failed to publish message. Id: {}. Data: {}. Cause: {}. ",
                                id, data, failure.getLocalizedMessage())
                );
    }

    public void publish(Number id, Object data) {
        publish(String.valueOf(id), data);
    }

    @Bean
    private NewTopic core() {
        return new NewTopic(TOPIC, 1, (short) 1);
    }
}
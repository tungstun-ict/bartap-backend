package com.tungstun.core.port.messaging;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaCoreMessageProducer {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaCoreMessageProducer.class);
    private static final String TOPIC_NAME = "core";

    private final KafkaTemplate<String, Object> template;

    public KafkaCoreMessageProducer(KafkaTemplate<String, Object> template) {
        this.template = template;
    }

    public void publish(String id, Object data) {
        template.send(TOPIC_NAME, id, data)
                .addCallback(
                        success -> LOG.info("Published message {} with id {}", data, id),
                        failure -> LOG.warn("Failed to publish message. Id: {}. Data: {}. Cause: {}. ",
                                id, data, failure.getLocalizedMessage())
                );
    }

    @Bean
    public NewTopic core() {
        return new NewTopic(TOPIC_NAME, 1, (short) 1);
    }
}

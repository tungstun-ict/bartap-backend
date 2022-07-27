package com.tungstun.common.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;


public class KafkaMessageProducer {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaMessageProducer.class);

    private final String topic;

    @Autowired
    private KafkaTemplate<String, Object> template;

    public KafkaMessageProducer(String topic) {
        this.topic = topic;
    }

    public void publish(Number id, Object data) {
        publish(String.valueOf(id), data);
    }

    public void publish(String id, Object data) {
        template.send(topic, id, data)
                .addCallback(
                        success -> LOG.info("Published message {} with id {}", data, id),
                        failure -> LOG.warn("Failed to publish message. Id: {}. Data: {}. Message: {}. ",
                                id, data, failure.getLocalizedMessage())
                );
    }
}

package com.tungstun.common.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;


public class KafkaMessageProducer {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaMessageProducer.class);

    private final String topic;
    private final KafkaTemplate<String, Object> template;

    public KafkaMessageProducer(String topic, KafkaTemplate<String, Object> template) {
        this.topic = topic;
        this.template = template;
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

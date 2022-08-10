package com.tungstun.common.test;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.Future;

/**
 * Base Class for Consumer Tests.
 * Class contains a way of publishing messages to a specified topic, to test a Consumer class.
 * {@code topic} defines topic name to publish messages to.
 * Class creates new topic if it does not exist yet with name of {@code topic}
 */
// Not a Component or other Bean, because it would be loaded during non-test runtime and break the application
public class MessageConsumerTestBase extends EmbeddedKafkaTestBase {
    @Autowired
    private KafkaTemplate<String, Object> template;

    @Bean
    public NewTopic createNewTopic() {
        return new NewTopic(topic, PARTITIONS, (short) 1);
    }

    /**
     * Publishes an event that is to be consumed in a test
     */
    protected void publishEvent(String key, Object value) {
        template.send(topic, key, value);
    }

    /**
     * Publishes an event that is to be consumed in a test
     */
    protected void publishEvent(Number key, Object value) {
        Future<SendResult<String, Object>> future = template.send(topic, String.valueOf(key), value);
        while (true) {
            if (future.isDone()) return;
        }
    }
}

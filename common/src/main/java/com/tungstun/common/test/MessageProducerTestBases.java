package com.tungstun.common.test;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Base Class for Producer Tests.
 * Class contains a way of receiving messages from a specified topic.
 * Class contains static assertion methods to check key and the data's object-type
 * {@code topic} defines topic name to listen on for messages.
 */
// Not a Component or other Bean, because it would be loaded during non-test runtime and break the application
public class MessageProducerTestBases extends EmbeddedKafkaTestBase {
    protected BlockingQueue<ConsumerRecord<String, String>> records;
    protected KafkaMessageListenerContainer<String, String> container;

    /**
     * Should be called in test class's {@code @BeforeEach setUp()} method
     */
    protected void setUp() {
        Map<String, Object> configs = new HashMap<>(
                KafkaTestUtils.consumerProps("consumer", "false", embeddedKafkaBroker));

        DefaultKafkaConsumerFactory<String, String> consumerFactory =
                new DefaultKafkaConsumerFactory<>(
                        configs,
                        new StringDeserializer(),
                        new StringDeserializer());

        records = new LinkedBlockingQueue<>();

        ContainerProperties containerProperties = new ContainerProperties(topic);
        container = new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);
        container.setupMessageListener((MessageListener<String, String>) records::add);
        container.start();

        ContainerTestUtils.waitForAssignment(container, embeddedKafkaBroker.getPartitionsPerTopic());
    }

    /**
     * Should be called in test class's {@code @AfterEach teardown()} method
     * */
    protected void tearDown() {
        container.stop();
    }


    /**
     * Converts {@code eventKey} number to String and calls {@code assertEventKey(...)}
     * */
    protected static void assertEventKey(Number eventKey, ConsumerRecord<?, ?> singleRecord) {
        assertEventKey(String.valueOf(eventKey), singleRecord);
    }

    /**
     * Assert if given record's key equals given {@code eventKey}
     * */
    protected static void assertEventKey(String eventKey, ConsumerRecord<?, ?> singleRecord) {
        String recordKey = (String) singleRecord.key();
        assertEquals(eventKey, recordKey);
    }

    /**
     * Assert if given record's value Type Id is given {@code eventType}
     * */
    protected static void assertEventType(String eventType, ConsumerRecord<?, ?> singleRecord) {
        byte[] bytes = singleRecord.headers().lastHeader("__TypeId__").value();
        String recordEventType = new String(bytes, StandardCharsets.UTF_8);
        assertEquals(eventType, recordEventType);
    }
}

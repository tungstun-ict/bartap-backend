package com.tungstun.common.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

/**
 * Abstract test base for the Consumer and Producer testbases.
 * Annotations needed to run an embedded kafka instance for tests included.
 * Basic fields needed for Consumer en Producer testbases.
 * */
@Configuration
@DirtiesContext
@EnableKafka
@EmbeddedKafka(
        partitions = KafkaTestBase.PARTITIONS,
        brokerProperties = {
                "listeners=PLAINTEXT://localhost:9092",
                "port=9092"
        }
)
public abstract class KafkaTestBase {
    protected static final int PARTITIONS = 1;

    protected static String topic;

    @Autowired
    protected EmbeddedKafkaBroker embeddedKafkaBroker;
}

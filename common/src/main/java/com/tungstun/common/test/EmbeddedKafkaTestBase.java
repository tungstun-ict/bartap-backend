package com.tungstun.common.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

/**
 * Abstract test base for the Consumer and Producer testbases.
 * Annotations needed to run an embedded kafka instance for tests included.
 * Basic fields needed for Consumer en Producer testbases.
 * */
// Not a Component or other Bean, because it would be loaded during non-test runtme and break the application
@DirtiesContext
@EmbeddedKafka(
        partitions = EmbeddedKafkaTestBase.PARTITIONS,
        brokerProperties = {
                "listeners=PLAINTEXT://${spring.kafka.bootstrap-servers:localhost:9092}"
        }
)
public abstract class EmbeddedKafkaTestBase {
    protected static final int PARTITIONS = 1;

    protected static String topic;

    @Autowired
    protected EmbeddedKafkaBroker embeddedKafkaBroker;
}

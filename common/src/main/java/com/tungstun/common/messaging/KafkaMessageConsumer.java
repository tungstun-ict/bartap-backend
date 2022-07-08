package com.tungstun.common.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;

public class KafkaMessageConsumer {
    protected static final Logger LOG = LoggerFactory.getLogger(KafkaMessageConsumer.class);

    @KafkaHandler(isDefault = true)
    public void unknown(Object o) {
        LOG.warn("Received unknown: {}", o);
    }
}

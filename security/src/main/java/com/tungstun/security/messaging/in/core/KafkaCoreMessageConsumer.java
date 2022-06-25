package com.tungstun.security.messaging.in.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(
        id = "coreMessageListener",
        topics = "core",
        containerFactory = "securityCoreListenerContainerFactory")
public class KafkaCoreMessageConsumer {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaCoreMessageConsumer.class);

//    @KafkaHandler
//    public void handleCoreMessage(BarDeleted o) {
//          //Revoke auth of deleted bar ?
//        LOG.info("Received CoreMessage: {}", o);
//    }

    @KafkaHandler(isDefault = true)
    public void unknown(Object o) {
        LOG.warn("Received unknown: {}", o);
    }
}
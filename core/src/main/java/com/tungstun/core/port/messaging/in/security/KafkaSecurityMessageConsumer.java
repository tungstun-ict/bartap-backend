package com.tungstun.core.port.messaging.in.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(id = "securityMessageListener", topics = "security")
public class KafkaSecurityMessageConsumer {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaSecurityMessageConsumer.class);
    
    @KafkaHandler(isDefault = true)
    public void unknown(Object o) {
        LOG.warn("Received unknown: {}", o);
    }
}

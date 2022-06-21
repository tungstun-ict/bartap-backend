package com.tungstun.security.messaging;

import com.tungstun.security.messaging.out.UserCreated;
import com.tungstun.security.messaging.out.UserEdited;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(id = "securityMessageListener", topics = "security")
public class KafkaSecurityMessageConsumer {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaSecurityMessageConsumer.class);

    @KafkaHandler
    public void handleUserCreated(UserCreated o) {
        LOG.info("Received UserCreated: {}", o);
    }

    @KafkaHandler
    public void handleUserEdited(UserEdited o) {
        LOG.info("Received UserEdited: {}", o);
    }

    @KafkaHandler(isDefault = true)
    public void unknown(Object o) {
        LOG.warn("Received unknown: {}", o);
    }
}

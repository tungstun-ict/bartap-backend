package com.tungstun.security.port.messaging.in.core;

import com.tungstun.security.application.authorization.AuthorizationCommandHandler;
import com.tungstun.security.application.authorization.command.AuthorizeNewBarOwnership;
import com.tungstun.security.port.messaging.in.core.message.BarCreated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(id = "coreMessageListener", topics = "core")
public class KafkaCoreMessageConsumer {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaCoreMessageConsumer.class);

    private final AuthorizationCommandHandler commandHandler;

    public KafkaCoreMessageConsumer(AuthorizationCommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @KafkaHandler
    public void handleCoreMessage(BarCreated o) {
        LOG.info("Received BarCreated: {}", o);
        commandHandler.handle(new AuthorizeNewBarOwnership(o.barId(), o.userId()));
    }

    @KafkaHandler(isDefault = true)
    public void unknown(Object o) {
        LOG.warn("Received unknown: {}", o);
    }
}
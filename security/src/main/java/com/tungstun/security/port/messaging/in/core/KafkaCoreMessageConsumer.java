package com.tungstun.security.port.messaging.in.core;

import com.tungstun.common.messaging.KafkaMessageConsumer;
import com.tungstun.security.application.authorization.AuthorizationCommandHandler;
import com.tungstun.security.application.authorization.command.AuthorizeNewBarOwnership;
import com.tungstun.security.port.messaging.in.core.message.BarCreated;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(id = "coreMessageListener", topics = "core")
public class KafkaCoreMessageConsumer extends KafkaMessageConsumer {
    private final AuthorizationCommandHandler commandHandler;

    public KafkaCoreMessageConsumer(AuthorizationCommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @KafkaHandler
    public void handleCoreMessage(BarCreated o) {
        LOG.info("Received BarCreated: {}", o);
        commandHandler.handle(new AuthorizeNewBarOwnership(o.barId(), o.userId()));
    }
}

package com.tungstun.person.port.messaging.in.user;

import com.tungstun.common.messaging.KafkaMessageConsumer;
import com.tungstun.person.application.user.UserCommandHandler;
import com.tungstun.person.application.user.command.CreateUser;
import com.tungstun.person.application.user.command.DeleteUser;
import com.tungstun.person.application.user.command.UpdateUser;
import com.tungstun.person.port.messaging.in.user.message.UserCreated;
import com.tungstun.person.port.messaging.in.user.message.UserDeleted;
import com.tungstun.person.port.messaging.in.user.message.UserUpdated;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@KafkaListener(id = "securityMessageListener", topics = "security")
public class KafkaUserMessageConsumer extends KafkaMessageConsumer {
    private final UserCommandHandler commandHandler;

    public KafkaUserMessageConsumer(UserCommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @KafkaHandler
    public void handleUserCreated(UserCreated event) {
        LOG.info("Received UserCreated: {}", event);
        String fullName = Objects.toString(event.firstName(), "")
                + Objects.toString(event.lastName(), "");

        commandHandler.handle(new CreateUser(
                event.id(),
                event.username(),
                event.phoneNumber(),
                fullName
        ));
    }

    @KafkaHandler
    public void handleUserUpdated(UserUpdated event) {
        LOG.info("Received UserUpdated: {}", event);
        String fullName = Objects.toString(event.firstName(), "")
                + Objects.toString(event.lastName(), "");

        commandHandler.handle(new UpdateUser(
                event.id(),
                event.username(),
                event.phoneNumber(),
                fullName
        ));
    }

    @KafkaHandler
    public void handleUserDeleted(UserDeleted event) {
        LOG.info("Received UserDeleted: {}", event);

        commandHandler.handle(new DeleteUser(event.id()));
    }
}

package com.tungstun.bill.port.messaging.in.person;

import com.tungstun.bill.application.person.PersonCommandHandler;
import com.tungstun.bill.application.person.command.CreatePerson;
import com.tungstun.bill.application.person.command.DeletePerson;
import com.tungstun.bill.application.person.command.UpdatePerson;
import com.tungstun.bill.port.messaging.in.person.message.PersonCreated;
import com.tungstun.bill.port.messaging.in.person.message.PersonDeleted;
import com.tungstun.bill.port.messaging.in.person.message.PersonUpdated;
import com.tungstun.common.messaging.KafkaMessageConsumer;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(id = "personMessageListener", topics = "person")
public class KafkaPersonMessageConsumer extends KafkaMessageConsumer {
    private final PersonCommandHandler commandHandler;

    public KafkaPersonMessageConsumer(PersonCommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @KafkaHandler
    public void handlePersonCreated(PersonCreated event) {
        LOG.info("Received PersonCreated: {}", event);
        commandHandler.handle(new CreatePerson(event.id(), event.barId(), event.username()));
    }

    @KafkaHandler
    public void handlePersonUpdated(PersonUpdated event) {
        LOG.info("Received PersonUpdated: {}", event);
        commandHandler.handle(new UpdatePerson(event.id(), event.username()));
    }

    @KafkaHandler
    public void handlePersonDeleted(PersonDeleted event) {
        LOG.info("Received PersonDeleted: {}", event);
        commandHandler.handle(new DeletePerson(event.id()));
    }
}

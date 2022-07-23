package com.tungstun.bill.port.messaging.in.person;

import com.tungstun.bill.domain.person.Person;
import com.tungstun.bill.domain.person.PersonRepository;
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
    private final PersonRepository repository;

    public KafkaPersonMessageConsumer(PersonRepository repository) {
        this.repository = repository;
    }

    @KafkaHandler
    public void handlePersonCreated(PersonCreated o) {
        LOG.info("Received PersonCreated: {}", o);
        repository.save(new Person(o.barId(), o.id(), o.username()));
    }

    @KafkaHandler
    public void handlePersonUpdated(PersonUpdated o) {
        LOG.info("Received PersonUpdated: {}", o);
        Person person = repository.findById(o.id())
                .orElseThrow();
        person.setUsername(o.username());
        repository.update(person);
    }

    @KafkaHandler
    public void handlePersonDeleted(PersonDeleted o) {
        LOG.info("Received PersonDeleted: {}", o);
        repository.delete(o.id());
    }
}

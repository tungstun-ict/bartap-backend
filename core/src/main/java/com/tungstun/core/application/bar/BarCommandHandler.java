package com.tungstun.core.application.bar;

import com.tungstun.common.messaging.KafkaMessageProducer;
import com.tungstun.core.application.bar.command.CreateBar;
import com.tungstun.core.application.bar.command.DeleteBar;
import com.tungstun.core.application.bar.command.UpdateBar;
import com.tungstun.core.domain.bar.Bar;
import com.tungstun.core.domain.bar.BarRepository;
import com.tungstun.core.port.messaging.out.bar.message.BarCreated;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;

@Service
@Validated
@Transactional
public class BarCommandHandler {
    private final BarRepository repository;
    private final KafkaMessageProducer producer;

    public BarCommandHandler(BarRepository repository, KafkaMessageProducer producer) {
        this.repository = repository;
        this.producer = producer;
    }

    public Long handle(@Valid CreateBar command) {
        Long barId = repository.save(new Bar(command.name(),
                command.address(),
                command.mail(),
                command.phoneNumber())
        ).getId();

        producer.publish(barId, new BarCreated(barId));

        return barId;
    }

    public void handle(@Valid UpdateBar command) {
        Bar bar = repository.findById(command.id())
                .orElseThrow(() -> new EntityNotFoundException(String.format("No bar found with id %s", command.id())));
        bar.setName(command.name());
        bar.setAddress(command.address());
        bar.setMail(command.mail());
        bar.setPhoneNumber(command.phoneNumber());
        repository.update(bar);
    }

    public void handle(@Valid DeleteBar command) {
        repository.delete(command.id());

        producer.publish(command.id(), new BarCreated(command.id()));
    }
}

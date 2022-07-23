package com.tungstun.person.application.person;

import com.tungstun.common.messaging.KafkaMessageProducer;
import com.tungstun.person.application.person.command.CreatePerson;
import com.tungstun.person.application.person.command.DeletePerson;
import com.tungstun.person.application.person.command.UpdatePerson;
import com.tungstun.person.application.person.event.PersonCreated;
import com.tungstun.person.application.person.event.PersonDeleted;
import com.tungstun.person.application.person.event.PersonUpdated;
import com.tungstun.person.domain.person.Person;
import com.tungstun.person.domain.person.PersonBuilder;
import com.tungstun.person.domain.person.PersonRepository;
import com.tungstun.person.domain.user.User;
import com.tungstun.person.domain.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;

@Service
@Validated
@Transactional
public class PersonCommandHandler {
    private final PersonRepository repository;
    private final UserRepository userRepository;
    private final KafkaMessageProducer producer;

    public PersonCommandHandler(PersonRepository repository, UserRepository userRepository, KafkaMessageProducer producer) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.producer = producer;
    }

    private Person loadPerson(Long id, Long barId) {
        return repository.findByIdAndBarId(id, barId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No person found with id %s", id)));
    }

    private User loadUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No user found with id %s", userId)));
    }

    public Long handle(@Valid CreatePerson command) {
        PersonBuilder personBuilder = new PersonBuilder(command.barId())
                .setName(command.name());
        if (command.userId() != null) {
            User user = loadUser(command.userId());
            personBuilder.setUser(user);
        }
        Person person = repository.save(personBuilder.build());

        producer.publish(person.getId(), new PersonCreated(person.getId(), person.getBarId(), person.getUser().getId(), person.getName()));

        return person.getId();
    }

    public Long handle(@Valid UpdatePerson command) {
        Person person = loadPerson(command.personId(), command.barId());
        person.setName(command.name());
        Long id = repository.update(person).getId();

        producer.publish(person.getId(), new PersonUpdated(person.getId(), person.getBarId(), person.getUser().getId(), person.getName()));

        return id;
    }

    public void handle(@Valid DeletePerson command) {
        Person person = loadPerson(command.personId(), command.barId());
        repository.delete(person);

        producer.publish(person.getId(), new PersonDeleted(person.getId()));
    }
}

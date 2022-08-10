
package com.tungstun.bill.application.person;

import com.tungstun.bill.application.person.command.CreatePerson;
import com.tungstun.bill.application.person.command.DeletePerson;
import com.tungstun.bill.application.person.command.UpdatePerson;
import com.tungstun.bill.domain.person.Person;
import com.tungstun.bill.domain.person.PersonRepository;
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

    public PersonCommandHandler(PersonRepository repository) {
        this.repository = repository;
    }

    public Person handle(@Valid CreatePerson command) {
        return repository.save(new Person(
                command.id(),
                command.barId(),
                command.username()
        ));
    }

    public Person handle(@Valid UpdatePerson command) {
        Person person = repository.findById(command.id())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Person with id %s could not be found", command.id())));
        person.setUsername(command.username());
        return repository.update(person);
    }

    public void handle(@Valid DeletePerson command) {
        repository.delete(command.id());
    }
}

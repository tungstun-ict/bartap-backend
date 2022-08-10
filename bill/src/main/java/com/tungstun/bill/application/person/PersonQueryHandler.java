package com.tungstun.bill.application.person;

import com.tungstun.bill.application.person.query.GetPerson;
import com.tungstun.bill.domain.person.Person;
import com.tungstun.bill.domain.person.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@Service
@Validated
public class PersonQueryHandler {
    private final PersonRepository repository;

    public PersonQueryHandler(PersonRepository repository) {
        this.repository = repository;
    }

    public Person handle(@Valid GetPerson query) {
        return repository.findById(query.id())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Person with id %s could not be found", query.id())));
    }
}

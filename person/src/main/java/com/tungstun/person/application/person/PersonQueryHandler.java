package com.tungstun.person.application.person;

import com.tungstun.person.application.person.query.GetPerson;
import com.tungstun.person.application.person.query.ListAllPeopleOfBar;
import com.tungstun.person.domain.person.Person;
import com.tungstun.person.domain.person.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@Service
@Validated
public class PersonQueryHandler {
    private final PersonRepository repository;

    public PersonQueryHandler(PersonRepository repository) {
        this.repository = repository;
    }

    public Person handle(@Valid GetPerson query) {
        return repository.findByIdAndBarId(query.personId(), query.barId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("No Person found with id %s", query.personId())));
    }

    public List<Person> handle(@Valid ListAllPeopleOfBar query) {
        return repository.findAllOfBar(query.barId());
    }
}

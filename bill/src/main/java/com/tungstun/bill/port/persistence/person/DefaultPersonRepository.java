package com.tungstun.bill.port.persistence.person;

import com.tungstun.bill.domain.person.Person;
import com.tungstun.bill.domain.person.PersonRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class DefaultPersonRepository implements PersonRepository {
    private final SpringPersonRepository repository;

    public DefaultPersonRepository(SpringPersonRepository repository) {
        this.repository = repository;
    }

    @Override
    public Person save(Person entity) {
        return repository.save(entity);
    }

    @Override
    public Person update(Person entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Person> findById(Long id) {
        return repository.findById(id);
    }
}

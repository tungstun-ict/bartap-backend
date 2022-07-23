package com.tungstun.person.port.persistence.person;

import com.tungstun.person.domain.person.Person;
import com.tungstun.person.domain.person.PersonRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    @Override
    public Optional<Person> findByIdAndBarId(Long id, Long barId) {
        return repository.findByIdAndBarId(id, barId);
    }

    @Override
    public List<Person> findAllOfBar(Long barId) {
        return repository.findAllByBarId(barId);
    }

    @Override
    public void delete(Person person) {
        repository.delete(person);
    }
}

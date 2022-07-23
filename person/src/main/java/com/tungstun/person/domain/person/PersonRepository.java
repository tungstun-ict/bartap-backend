package com.tungstun.person.domain.person;

import com.tungstun.common.persistence.CrudRepositoryFragment;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends CrudRepositoryFragment<Person, Long> {
    Optional<Person> findByIdAndBarId(Long id, Long barId);

    List<Person> findAllOfBar(Long barId);

    void delete(Person person);
}

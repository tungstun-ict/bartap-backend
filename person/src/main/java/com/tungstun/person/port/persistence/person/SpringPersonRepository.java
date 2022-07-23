package com.tungstun.person.port.persistence.person;

import com.tungstun.person.domain.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringPersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByIdAndBarId(Long id, Long barId);
}

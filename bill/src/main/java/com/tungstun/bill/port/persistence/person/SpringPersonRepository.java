package com.tungstun.bill.port.persistence.person;

import com.tungstun.bill.domain.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringPersonRepository extends JpaRepository<Person, Long> {
}

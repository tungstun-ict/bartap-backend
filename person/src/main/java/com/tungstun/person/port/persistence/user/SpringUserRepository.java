package com.tungstun.person.port.persistence.user;

import com.tungstun.person.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringUserRepository extends JpaRepository<User, Long> {
}

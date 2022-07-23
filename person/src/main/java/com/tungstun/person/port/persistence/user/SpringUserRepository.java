package com.tungstun.person.port.persistence.user;

import com.tungstun.person.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByIdAndBarId(Long id, Long barId);
}

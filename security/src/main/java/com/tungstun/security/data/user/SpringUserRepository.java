package com.tungstun.security.data.user;

import com.tungstun.security.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByMail(String mail);
    Optional<User> findByMailOrUsername(String mail, String username);
}

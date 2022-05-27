package com.tungstun.security.domain.user;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    Optional<User> findByMail(String mail);
    Optional<User> findByMailOrUsername(String mail, String username);
}
